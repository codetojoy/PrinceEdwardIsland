import * as c from "./const.js";

export function getFillColor(d) {
  return d.children ? c.color(d.depth) : c.PARTY_COLOR_MAP[d.data.party];
}

export function hasManyChildren(d) {
  let result = false;
  const isLeaf = d.data.children == null;
  if (isLeaf && d.parent && d.parent.data && d.parent.data.children) {
    const numNodes = d.parent.data.children.length;
    result = numNodes >= c.NUM_SIBLINGS_FOR_SMALL_TEXT;
  }
  return result;
}

function getTextClass(d) {
  let result = "label";

  if (isPremiersRequest()) {
    const isLeaf = d.data.children == null;
    if (isLeaf) {
      result = "label-tiny";
    }
  } else {
    if (hasManyChildren(d)) {
      if (d.data.name && d.data.name.length >= c.NUM_CHARS_FOR_TINY_TEXT) {
        result = "label-tiny";
      } else {
        result = "label-small";
      }
    }
  }

  return result;
}

export var myLocation = {
  getCurrentURL: function () {
    return window.location.href;
  },
};

export function getFileName() {
  return myLocation.getCurrentURL().split("/").slice(-1)[0];
}

export function isFrench(fileName) {
  return fileName.includes(c.FR_QUALIFIER);
}

export function isPremiers(fileName) {
  return fileName.includes(c.PREMIERS);
}

export function isElements(fileName) {
  return fileName.includes(c.ELEMENTS_QUALIFIER);
}

export function isFrenchRequest() {
  return isFrench(getFileName());
}

export function isPremiersRequest() {
  return isPremiers(getFileName());
}

export function getJsonFile(jsonFile) {
  const fileName = getFileName();
  const prefix = isPremiers(fileName) ? c.NORMAL_PREMIERS_PREFIX : c.NORMAL_PREFIX;
  const element = isElements(jsonFile) ? c.ELEMENTS_QUALIFIER : "";
  const lang = isFrench(fileName) ? c.FR_QUALIFIER : "";

  // e.g. MLAs, Signs, English       : zodiac.json
  // e.g. Premiers, Elements, French : zodiac_premiers_elements_fr.json
  const result = `./${prefix}${element}${lang}.json`;

  const isKnownFile = c.KNOWN_JSON_FILES.includes(result);
  if (!isKnownFile) {
    console.log(`TRACER INTERNAL ERROR ${result}`);
    throw "Internal error on JSON file";
  }

  return result;
}

export function getTextForLegend(legendKey) {
  if (isPremiersRequest() && legendKey === c.PC_PARTY_LEGEND) {
    // We want "Conservative/PC" legend text for "PC"
    return isFrenchRequest() ? c.COMBO_PC_CON_PARTY_FR : c.COMBO_PC_CON_PARTY;
  } else {
    return isFrenchRequest() ? c.LEGEND_MAP[legendKey].fr : legendKey;
  }
}

function getDeltaForLegend(legendKey) {
  return c.LEGEND_MAP[legendKey].delta;
}

function getColorForLegend(legendKey) {
  const colorMap = c.PARTY_COLOR_MAP;
  return colorMap[c.LEGEND_MAP[legendKey].code];
}

function getLegendKeys() {
  if (isPremiersRequest()) {
    return [c.LIBERAL_PARTY_LEGEND, c.PC_PARTY_LEGEND];
  } else {
    return [c.GREEN_PARTY_LEGEND, c.LIBERAL_PARTY_LEGEND, c.PC_PARTY_LEGEND];
  }
}

// ----------

export function updateNormalMode() {
  drawCircle(c.NORMAL_JSON_FILE);
}

function updateElementsMode() {
  drawCircle(c.ELEMENTS_JSON_FILE);
}

function drawHorizontalLegend() {
  let svg = d3.select("#legend");
  svg.append("rect").attr("height", "100%").attr("width", "100%").attr("fill", d3.color(c.BACKGROUND_LIGHT));

  let keys = getLegendKeys();

  const firstDotX = 210;
  const firstDotY = 20;
  const radius = 7;

  svg
    .selectAll("mydots")
    .data(keys)
    .enter()
    .append("circle")
    .attr("cx", function (d, i) {
      return firstDotX + i * getDeltaForLegend(d);
    })
    .attr("cy", firstDotY)
    .attr("r", radius)
    .style("fill", function (d) {
      return getColorForLegend(d);
    });

  const firstLabelX = firstDotX + 20;
  const firstLabelY = firstDotY + 7;

  svg
    .selectAll("mylabels")
    .data(keys)
    .enter()
    .append("text")
    .attr("x", function (d, i) {
      return firstLabelX + i * getDeltaForLegend(d);
    })
    .attr("y", firstLabelY)
    .style("fill", function (d) {
      return getColorForLegend(d);
    })
    .text(function (d) {
      return getTextForLegend(d);
    })
    .attr("text-anchor", "left")
    .style("alignment-baseline", "middle");
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

  d3.json(getJsonFile(jsonFile), function (error, root) {
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

    drawHorizontalLegend();

    let node = g.selectAll("circle,text");

    svg.style("background", c.color(-1)).on("click", function () {
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
  if (value === c.MODE_NORMAL) {
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
