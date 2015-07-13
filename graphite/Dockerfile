FROM debian:7.5
MAINTAINER Steve Spagnola <stevesie@stevesie.com>

RUN apt-get update
RUN apt-get install -y wget
RUN cd /root

# Install graphite
RUN apt-get install -y python2.6 python-pip python-cairo python-django python-django-tagging
RUN apt-get install -y libapache2-mod-wsgi python-twisted python-memcache python-pysqlite2 python-simplejson

RUN pip install whisper
RUN pip install carbon
RUN pip install graphite-web

# ADD graphite/apache.conf /etc/apache2/apache2.conf
ADD conf/vhost /etc/apache2/sites-available/graphite

RUN cp /opt/graphite/conf/graphite.wsgi.example /opt/graphite/conf/graphite.wsgi
RUN cp /opt/graphite/conf/carbon.conf.example /opt/graphite/conf/carbon.conf

ADD conf/storage-schemas.conf /opt/graphite/conf/storage-schemas.conf
ADD conf/storage-aggregation.conf /opt/graphite/conf/storage-aggregation.conf

ADD conf/local_settings.py /opt/graphite/webapp/graphite/local_settings.py

RUN export LANG="en_US.UTF-8"

RUN cd /opt/graphite/webapp/graphite && python manage.py syncdb --noinput

RUN chown -R www-data:www-data /opt/graphite/storage/

RUN a2dissite default
RUN a2ensite graphite

# INSTALL STATS D

RUN apt-get install -y curl git screen
RUN curl -sL https://deb.nodesource.com/setup | bash -
RUN apt-get install -y nodejs

# INSTALL STATS D
RUN cd /opt && git clone https://github.com/etsy/statsd
ADD conf/local.js /opt/statsd/local.js
RUN chmod -R 777 /var/run/screen

ADD conf/start.sh /start.sh
RUN chmod 755 /start.sh

CMD ["/bin/bash", "/start.sh"]
