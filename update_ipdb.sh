#!/bin/sh

# This script downloads the latest ip 2 country database from maxmind, and writes
# it to the file thd admin will read from once it's compiled.

mkdir -p src/main/config
curl -s http://www.maxmind.com/download/geoip/database/GeoLiteCountry/GeoIP.dat.gz | gunzip >  src/main/config/GeoIP.dat 
