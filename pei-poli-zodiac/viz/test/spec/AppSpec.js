describe("App", function () {
  beforeEach(function () {});

  describe("getFillColor", function () {
    it("should be able to get color for non-leaf node", function () {
      const d = { depth: 0, children: [{ name: "mozart" }] };

      // test
      const result = getFillColor(d);
      expect(result).toEqual("rgb(220, 225, 147)");
    });

    it("should be able to get color for leaf node", function () {
      const d = { data: { party: GREEN_PARTY } };

      // test
      const result = getFillColor(d);

      expect(result).toEqual(d3.rgb(64, 157, 74));
    });
  });
});
