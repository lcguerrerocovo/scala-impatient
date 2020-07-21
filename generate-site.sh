#!/bin/bash

set -o errexit
set -o nounset
set -o pipefail

# generate github pages site
sbt "clean; compile;"
mv *.html docs/
