// ----------

const JSON_FILE = "./zodiac.json";
const BACKGROUND_LIGHT = "hsl(61,80%,80%)";
const BACKGROUND_DARK = "hsl(80,30%,40%)";
const BACKGROUND_RANGE = [BACKGROUND_LIGHT, BACKGROUND_DARK];

const GREEN_PARTY = "Green";
const LIBERAL_PARTY = "Liberal";
const NDP_PARTY = "NDP";
const PC_PARTY = "PC";
const UNKNOWN_PARTY = "Unknown";

// ----------

function getFillColor(d) {
  let result = null;
  if (d.children) {
    if (d.data.name === UNKNOWN_PARTY) {
      result = d3.color("white");
    } else {
      result = color(d.depth);
    }
  } else {
    let party = d.data.party;
    // colours sampled from party websites
    if (party === GREEN_PARTY) {
      // x40, x9D, x4A
      result = d3.rgb(64, 157, 74);
    } else if (party === LIBERAL_PARTY) {
      // E0, 1F, 2B
      result = d3.rgb(224, 31, 43);
    } else if (party === NDP_PARTY) {
      // F3, 82, 30
      result = d3.rgb(243, 50, 48);
    } else if (party === PC_PARTY) {
      // 2E, 85, C4
      result = d3.rgb(46, 133, 196);
    }
  }
  return result;
}

// ----------

let svg = d3.select("#known"),
  margin = 20,
  diameter = +svg.attr("width"),
  g = svg.append("g").attr("transform", "translate(" + diameter / 2 + "," + diameter / 2 + ")");

let color = d3.scaleLinear().domain([-1, 5]).range(BACKGROUND_RANGE).interpolate(d3.interpolateHcl);

let pack = d3
  .pack()
  .size([diameter - margin, diameter - margin])
  .padding(2);

d3.json(JSON_FILE, function (error, root) {
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
    view;

  let circle = g
    .selectAll("circle")
    .data(nodes)
    .enter()
    .append("circle")
    .attr("class", function (d) {
      return d.parent ? (d.children ? "node" : "node node--leaf") : "node node--root";
    })
    .style("fill", function (d) {
      return getFillColor(d);
    })
    .on("click", function (d) {
      if (focus !== d) zoom(d), d3.event.stopPropagation();
    });

  let text = g
    .selectAll("text")
    .data(nodes)
    .enter()
    .append("text")
    .attr("class", "label")
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

  function zoom(d) {
    let focus0 = focus;
    focus = d;

    let transition = d3
      .transition()
      .duration(d3.event.altKey ? 7500 : 750)
      .tween("zoom", function (d) {
        let i = d3.interpolateZoom(view, [focus.x, focus.y, focus.r * 2 + margin]);
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

  function zoomTo(v) {
    let k = diameter / v[2];
    view = v;
    node.attr("transform", function (d) {
      return "translate(" + (d.x - v[0]) * k + "," + (d.y - v[1]) * k + ")";
    });
    circle.attr("r", function (d) {
      return d.r * k;
    });
  }
});
