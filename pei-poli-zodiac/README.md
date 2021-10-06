### Zodiac Signs for PEI MLAs (Members of Legislative Assembly)

- A silly project, inspired by [this tweet](https://twitter.com/perry_chel/status/1437800478897758212)
  - Uses the amazing [D3.js library](https://d3js.org)
  - Inspired, specifically, by [this example](https://gist.github.com/mbostock/4063530)
- The intent here is some levity during a pandemic.
- Data-set is [here](./zodiac.csv)
  - see License Details below
- All birthdays/signs found on [Wikipedia](https://wikipedia.org) or via public tweets.
  - sources listed in the data file
- Names, titles, ridings from [this page](https://www.assembly.pe.ca/members)
  - current as of 29-SEP-2021
- Contributions welcome, provided info is disclosed by MLA in public.

### Technical details

- original data-set is `./zodiac.csv`
- this project requires Gradle and Groovy (and Java JDK)
- `./src/json-generator` produces `./zodiac.json` from `./zodiac.csv`
- `./viz` is the project for the [circle-packing visualization](https://codetojoy.github.io/PrinceEdwardIsland/web/pei-poli-zodiac/index.html)
  - `gh-pages` branch contains [info.html](https://codetojoy.github.io/PrinceEdwardIsland/web/pei-poli-zodiac/info.html)
  - `gh-pages` branch contains `VersionInfo.groovy`, which creates timestamp, and `publish.sh` which commits to the public-facing branch

### License details

- [This data file](./zodiac.csv) is licensed under a [Creative Commons Attribution-ShareAlike 4.0 International License](http://creativecommons.org/licenses/by-sa/4.0/).
- The code and website are licensed via [Apache License Version 2.0](./LICENSE).

### Workflow for data update

- edit `./zodiac.csv`
- `cd ~/src/json_generator`
- `./run.sh` will run unit tests and generate the JSON files
- `cd ~/viz`
- `./server.sh` will run local HTTP server for testing
- commit to master branch
- cd ~/../PrinceEdwardIsland-gh-pages
- edit `refresh-zodiac.sh` so that paths are correct
- `./publish.sh` will copy files, run version info, and commit to `gh-pages`
