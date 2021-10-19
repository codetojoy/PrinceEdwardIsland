// ----------

const NORMAL_JSON_FILE = "./zodiac.json";
const ELEMENTS_JSON_FILE = "./zodiac_elements.json";
const BACKGROUND_LIGHT = "hsl(61,80%,80%)";
const BACKGROUND_DARK = "hsl(80,30%,40%)";
const BACKGROUND_RANGE = [BACKGROUND_LIGHT, BACKGROUND_DARK];

const GREEN_PARTY = "Green";
const LIBERAL_PARTY = "Liberal";
const NDP_PARTY = "NDP";
const PC_PARTY = "PC";
const UNKNOWN_PARTY = "Unknown";

const NUM_SIBLINGS_FOR_SMALL_TEXT = 4;
const NUM_CHARS_FOR_TINY_TEXT = 14;

const MODE_NORMAL = "normal";
const MODE_ELEMENTS = "elements";

// colours sampled from party websites
const PARTY_COLOR_MAP = {
  [GREEN_PARTY]: d3.rgb(64, 157, 74),
  [LIBERAL_PARTY]: d3.rgb(224, 31, 43),
  [NDP_PARTY]: d3.rgb(243, 50, 48),
  [PC_PARTY]: d3.rgb(46, 133, 196),
  [UNKNOWN_PARTY]: d3.color("white"),
};

const color = d3.scaleLinear().domain([-1, 5]).range(BACKGROUND_RANGE).interpolate(d3.interpolateHcl);

// ----------

function getFillColor(d) {
  return d.children ? color(d.depth) : PARTY_COLOR_MAP[d.data.party];
}

function hasManyChildren(d) {
  let result = false;
  const isLeaf = d.data.children == null;
  if (isLeaf && d.parent && d.parent.data && d.parent.data.children) {
    const numNodes = d.parent.data.children.length;
    result = numNodes >= NUM_SIBLINGS_FOR_SMALL_TEXT;
  }
  return result;
}

function getTextClass(d) {
  let result = "label";

  if (hasManyChildren(d)) {
    if (d.data.name && d.data.name.length >= NUM_CHARS_FOR_TINY_TEXT) {
      result = "label-tiny";
    } else {
      result = "label-small";
    }
  }

  return result;
}

// ----------

function updateNormalMode() {
  drawCircle(NORMAL_JSON_FILE);
}

function updateElementsMode() {
  drawCircle(ELEMENTS_JSON_FILE);
}

function drawCircle(jsonFile) {
  let svg = d3.select("#known"),
    margin = 20,
    diameter = +svg.attr("width"),
    radius = diameter / 2,
    g = svg.append("g").attr("transform", `translate(${radius}, ${radius})`);

  // clear any previous graph, esp. if zoomed in
  svg.selectAll("circle").remove();

  let pack = d3
    .pack()
    .size([diameter - margin, diameter - margin])
    .padding(2);

  d3.json(jsonFile, function (error, root) {
    if (error) throw error;

    root = d3
      .hierarchy(root)
      .sum(function (d) {
        return d.size;
      })
      .sort(function (a, b) {
        return b.value - a.value;
      });

    let focus = root,
      nodes = pack(root).descendants(),
      currentView;

    let circle = g
      .selectAll("circle")
      .data(nodes)
      .enter()
      .append("circle")
      .attr("class", function (d) {
        return d.parent ? (d.children ? "node" : "node node--leaf") : "node node--root";
      })
      .style("fill", getFillColor)
      .on("click", function (d) {
        if (focus !== d) zoom(d), d3.event.stopPropagation();
      });

    let text = g
      .selectAll("text")
      .data(nodes)
      .enter()
      .append("text")
      .attr("class", getTextClass)
      .style("fill-opacity", function (d) {
        return d.parent === root ? 1 : 0;
      })
      .style("display", function (d) {
        return d.parent === root ? "inline" : "none";
      })
      .text(function (d) {
        return d.data.name;
      });

    let node = g.selectAll("circle,text");

    svg.style("background", color(-1)).on("click", function () {
      zoom(root);
    });

    zoomTo([root.x, root.y, root.r * 2 + margin]);

    // called when user clicks on a circle
    // sets up a tween animation where `zoomTo` is called repeatedly
    // focus - target
    // view
    //
    // https://observablehq.com/@d3/d3-interpolatezoom
    // - interpolation uses start, end view
    // - `view` is defined by x, y, width
    //    - x,y is center
    function zoom(d) {
      focus = d;

      let transition = d3
        .transition()
        .duration(d3.event.altKey ? 7500 : 750)
        .tween("zoom", function (d) {
          const targetView = [focus.x, focus.y, focus.r * 2 + margin];
          const i = d3.interpolateZoom(currentView, targetView);
          // let i = d3.interpolateZoom(currentView, [focus.x, focus.y, focus.r * 2 + margin]);
          return function (t) {
            zoomTo(i(t));
          };
        });

      transition
        .selectAll("text")
        .filter(function (d) {
          return d.parent === focus || this.style.display === "inline";
        })
        .style("fill-opacity", function (d) {
          return d.parent === focus ? 1 : 0;
        })
        .on("start", function (d) {
          if (d.parent === focus) this.style.display = "inline";
        })
        .on("end", function (d) {
          if (d.parent !== focus) this.style.display = "none";
        });
    }

    // v is a view: [node.x, node.y, width]
    // side-effects:
    //    each node (i.e. circle, text selection) is translated
    //    currentView is updated
    //    circle selection: radius scaled
    // see link above about interpolationZoom
    function zoomTo(v) {
      const k = diameter / v[2];
      currentView = v;
      node.attr("transform", function (d) {
        const x = (d.x - v[0]) * k;
        const y = (d.y - v[1]) * k;
        return `translate(${x},${y})`;
      });
      circle.attr("r", function (d) {
        return d.r * k;
      });
    }
  });
} // drawCircle

// ----------------- DOM/event handlers

function modeCheckboxHandler(event) {
  const value = event.target.value;
  if (value === MODE_NORMAL) {
    updateNormalMode();
  } else {
    updateElementsMode();
  }
}

if (document) {
  if (document.getElementById("checkbox-normal")) {
    document.getElementById("checkbox-normal").addEventListener("change", modeCheckboxHandler);
  }
  if (document.getElementById("checkbox-elements")) {
    document.getElementById("checkbox-elements").addEventListener("change", modeCheckboxHandler);
  }
}
