FROM tomcat:8.5.4
MAINTAINER Cole Hansen <cole_hansen@brown.edu>

WORKDIR ./webapps

RUN echo "<html><head><script>document.location = '/mapviz';</script>\
</head><body></body></html>" > ./ROOT/index.jsp
