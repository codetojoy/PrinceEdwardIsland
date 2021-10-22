import * as app from "../../app.js";
import * as c from "../../const.js";

describe("App", function () {
  beforeEach(function () {});

  describe("getFillColor", function () {
    it("should be able to get color for non-leaf node", function () {
      const d = { depth: 0, children: [{ name: "mozart" }] };

      // test
      const result = app.getFillColor(d);
      expect(result).toEqual("rgb(220, 225, 147)");
    });

    it("should be able to get color for leaf node", function () {
      const d = { data: { party: c.GREEN_PARTY } };

      // test
      const result = app.getFillColor(d);

      expect(result).toEqual(d3.rgb(64, 157, 74));
    });
  });

  describe("hasManyChildren", function () {
    it("should be able to detect node with many children", function () {
      const d = {
        data: {},
        parent: {
          data: {
            children: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
          },
        },
      };

      // test
      const result = app.hasManyChildren(d);

      expect(result).toBeTruthy();
    });

    it("should be able to detect node with few children", function () {
      const d = {
        data: {},
        parent: {
          data: {
            children: [1, 2, 3],
          },
        },
      };

      // test
      const result = app.hasManyChildren(d);

      expect(result).toBeFalsy();
    });
  });
});
