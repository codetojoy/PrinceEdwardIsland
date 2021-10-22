
export const NORMAL_JSON_FILE = "./zodiac.json";
export const ELEMENTS_JSON_FILE = "./zodiac_elements.json";
export const BACKGROUND_LIGHT = "hsl(61,80%,80%)";
export const BACKGROUND_DARK = "hsl(80,30%,40%)";
export const BACKGROUND_RANGE = [BACKGROUND_LIGHT, BACKGROUND_DARK];

export const GREEN_PARTY = "Green";
export const LIBERAL_PARTY = "Liberal";
export const NDP_PARTY = "NDP";
export const PC_PARTY = "PC";
export const UNKNOWN_PARTY = "Unknown";

export const NUM_SIBLINGS_FOR_SMALL_TEXT = 4;
export const NUM_CHARS_FOR_TINY_TEXT = 14;

export const MODE_NORMAL = "normal";
export const MODE_ELEMENTS = "elements";

// colours sampled from party websites
export const PARTY_COLOR_MAP = {
  [GREEN_PARTY]: d3.rgb(64, 157, 74),
  [LIBERAL_PARTY]: d3.rgb(224, 31, 43),
  [NDP_PARTY]: d3.rgb(243, 50, 48),
  [PC_PARTY]: d3.rgb(46, 133, 196),
  [UNKNOWN_PARTY]: d3.color("white"),
};

export const color = d3.scaleLinear().domain([-1, 5]).range(BACKGROUND_RANGE).interpolate(d3.interpolateHcl);

