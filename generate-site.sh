#!/bin/bash

set -o errexit
set -o nounset
set -o pipefail

# generate github pages site
sbt compile
mv *.html docs/
