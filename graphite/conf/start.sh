#!/bin/bash

service apache2 start;

/opt/graphite/bin/carbon-cache.py start;

sleep 10;

#odd graphite issue...
service apache2 restart;

cd /opt/statsd && node stats.js local.js;
