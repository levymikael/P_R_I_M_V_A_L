#!/bin/bash

exec docker run --rm -i -v $PWD:/work amake/innosetup "$@"