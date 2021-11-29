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

  describe("getFileName", function () {
    it("should have basic functionality", function () {
      spyOn(app.myLocation, "getCurrentURL").and.returnValue(
        "https://codetojoy.github.io/PrinceEdwardIsland/web/pei-poli-zodiac/index_fr.html"
      );

      // test
      const result = app.getFileName();

      expect(result).toEqual("index_fr.html");
    });
  });

  describe("isFrenchRequest", function () {
    it("should have default value of English request", function () {
      // test
      const result = app.isFrenchRequest();

      expect(result).toBeFalsy();
    });

    it("should detect French request", function () {
      spyOn(app.myLocation, "getCurrentURL").and.returnValue("./index_fr.html");

      // test
      const result = app.isFrenchRequest();

      expect(result).toBeTruthy();
    });
  });

  describe("isPremiersRequest", function () {
    it("should have default value of English request", function () {
      spyOn(app.myLocation, "getCurrentURL").and.returnValue("./index.html");

      // test
      const result = app.isPremiersRequest();

      expect(result).toBeFalsy();
    });

    it("should detect Premiers request", function () {
      spyOn(app.myLocation, "getCurrentURL").and.returnValue("./premiers.html");

      // test
      const result = app.isPremiersRequest();

      expect(result).toBeTruthy();
    });
  });

  describe("isFrench", function () {
    it("should have detect English", function () {
      const fileName = "index.html";
      // test
      const result = app.isFrench(fileName);

      expect(result).toBeFalsy();
    });

    it("should detect French qualifier", function () {
      const fileName = "index_fr.html";

      // test
      const result = app.isFrench(fileName);

      expect(result).toBeTruthy();
    });
  });

  describe("isPremiers", function () {
    it("should detect MLAs", function () {
      const fileName = "index.html";
      // test
      const result = app.isPremiers(fileName);

      expect(result).toBeFalsy();
    });

    it("should detect Premiers", function () {
      const fileName = "premiers.html";

      // test
      const result = app.isPremiers(fileName);

      expect(result).toBeTruthy();
    });
  });

  describe("isElements", function () {
    it("should detect normal Signs", function () {
      const fileName = "zodiac_premiers_fr.json";
      // test
      const result = app.isElements(fileName);

      expect(result).toBeFalsy();
    });

    it("should detect Elements qualifier", function () {
      const fileName = "zodiac_premiers_elements_fr.json";

      // test
      const result = app.isElements(fileName);

      expect(result).toBeTruthy();
    });
  });

  describe("getJsonFile", function () {
    it("should have value in MLAs, Signs, English", function () {
      spyOn(app.myLocation, "getCurrentURL").and.returnValue("./index.html");
      const jsonFile = "zodiac.json";

      // test
      const result = app.getJsonFile(jsonFile);

      expect(result).toEqual(c.NORMAL_JSON_FILE);
    });

    it("should have value in MLAs, Signs, French", function () {
      spyOn(app.myLocation, "getCurrentURL").and.returnValue("./index_fr.html");
      const jsonFile = "zodiac_fr.json";

      // test
      const result = app.getJsonFile(jsonFile);

      expect(result).toEqual(c.NORMAL_JSON_FR_FILE);
    });

    it("should have value in MLAs, Elements, English", function () {
      spyOn(app.myLocation, "getCurrentURL").and.returnValue("./index.html");
      const jsonFile = "zodiac_elements.json";

      // test
      const result = app.getJsonFile(jsonFile);

      expect(result).toEqual(c.ELEMENTS_JSON_FILE);
    });

    it("should have value in MLAs, Elements, French", function () {
      spyOn(app.myLocation, "getCurrentURL").and.returnValue("./index_fr.html");
      const jsonFile = "zodiac_elements_fr.json";

      // test
      const result = app.getJsonFile(jsonFile);

      expect(result).toEqual(c.ELEMENTS_JSON_FR_FILE);
    });

    it("should have value in Premiers, Signs, English", function () {
      spyOn(app.myLocation, "getCurrentURL").and.returnValue("./premiers.html");
      const jsonFile = "zodiac.json";

      // test
      const result = app.getJsonFile(jsonFile);

      expect(result).toEqual(c.NORMAL_PREMIERS_JSON_FILE);
    });

    it("should have value in Premiers, Signs, French", function () {
      spyOn(app.myLocation, "getCurrentURL").and.returnValue("./premiers_fr.html");
      const jsonFile = "zodiac_fr.json";

      // test
      const result = app.getJsonFile(jsonFile);

      expect(result).toEqual(c.NORMAL_PREMIERS_JSON_FR_FILE);
    });

    it("should have value in Premiers, Elements, English", function () {
      spyOn(app.myLocation, "getCurrentURL").and.returnValue("./premiers.html");
      const jsonFile = "zodiac_elements.json";

      // test
      const result = app.getJsonFile(jsonFile);

      expect(result).toEqual(c.ELEMENTS_PREMIERS_JSON_FILE);
    });

    it("should have value in Premiers, Elements, French", function () {
      spyOn(app.myLocation, "getCurrentURL").and.returnValue("./premiers_fr.html");
      const jsonFile = "zodiac_elements_fr.json";

      // test
      const result = app.getJsonFile(jsonFile);

      expect(result).toEqual(c.ELEMENTS_PREMIERS_JSON_FR_FILE);
    });
  });

  describe("getTextForLegend", function () {
    it("should have value in Signs, English", function () {
      // test
      const result = app.getTextForLegend(c.LIBERAL_PARTY_LEGEND);

      expect(result).toEqual(c.LIBERAL_PARTY_LEGEND);
    });

    it("should have value in Signs, French", function () {
      spyOn(app.myLocation, "getCurrentURL").and.returnValue("./index_fr.html");

      // test
      const result = app.getTextForLegend(c.LIBERAL_PARTY_LEGEND);

      expect(result).toEqual([c.LIBERAL_PARTY_LEGEND_FR]);
    });

    it("should have value in Premiers, English, Liberal", function () {
      spyOn(app.myLocation, "getCurrentURL").and.returnValue("./premiers.html");

      // test
      const result = app.getTextForLegend(c.LIBERAL_PARTY_LEGEND);

      expect(result).toEqual(c.LIBERAL_PARTY_LEGEND);
    });

    it("should have value in Premiers, French, Liberal", function () {
      spyOn(app.myLocation, "getCurrentURL").and.returnValue("./premiers_fr .html");

      // test
      const result = app.getTextForLegend(c.LIBERAL_PARTY_LEGEND);

      expect(result).toEqual([c.LIBERAL_PARTY_LEGEND_FR]);
    });

    it("should have combo value in Premiers, English, PC", function () {
      spyOn(app.myLocation, "getCurrentURL").and.returnValue("./premiers.html");

      // test
      const result = app.getTextForLegend(c.PC_PARTY_LEGEND);

      expect(result).toEqual(c.COMBO_PC_CON_PARTY);
    });

    it("should have combo value in Premiers, French, PC", function () {
      spyOn(app.myLocation, "getCurrentURL").and.returnValue("./premiers_fr.html");

      // test
      const result = app.getTextForLegend(c.PC_PARTY_LEGEND);

      expect(result).toEqual(c.COMBO_PC_CON_PARTY_FR);
    });
  });
});
