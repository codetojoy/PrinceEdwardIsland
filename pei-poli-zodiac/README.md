### Zodiac Signs for PEI MLAs

- A silly project, inspired by [this tweet](https://twitter.com/perry_chel/status/1437800478897758212)
  - Uses the amazing [D3.js library](https://d3js.org)
  - Inpsired, specifically, by [this example](https://gist.github.com/mbostock/4063530)
- The intent here is some levity during a pandemic.
- Data-set is [here](./zodiac.csv)
  - [license for this project](./LICENSE) (Apache License Version 2.0)
- All birthdays found on [Wikipedia](https://wikipedia.org)
- Names, titles, ridings from [this page](https://www.assembly.pe.ca/members)
  - current as of 28-SEP-2021
- Contributions welcome, provided info is available in public (or disclosed by MLA)

### Technical details

- original data-set is `./zodiac.csv`
- this project requires Gradle and Groovy (and Java JDK)
- `./src/json-generator` produces `./zodiac.json` from `./zodiac.csv`
- `./viz` is the project for the [circle-packing visualization](https://codetojoy.github.io/PrinceEdwardIsland/web/pei-poli-zodiac/index.html)
  - `gh-pages` branch contains [info.html](https://codetojoy.github.io/PrinceEdwardIsland/web/pei-poli-zodiac/info.html)
  - `gh-pages` branch contains `VersionInfo.groovy`, which creates timestamp, and `publish.sh` which commits to the public-facing branch
