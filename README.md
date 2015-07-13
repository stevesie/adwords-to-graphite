# AdWords to Graphite

Managing an AdWords account and want to actually review key performance metrics in real-time and historically?

Well, you're in luck.  This program will do just that, eventually... ihis is a work in progress right now and not intended to be read by anyone yet.

### Installation

Get setup with an AdWords developer account.  There is an approval process involved, so do this part first.

https://github.com/googleads/googleads-java-lib/wiki/Using-OAuth2.0

Get Graphite setup locally.

1. Install Docker

2. Build the Graphite Image

```
cd graphite
docker build -t stevesie/graphite .
docker run 80:80 -p 8125:8125 stevesie/graphite
boot2docker ip
```

Go to the address that the last command issued, and we should see Graphite.

... More to come, waiting for Google approval myself...
