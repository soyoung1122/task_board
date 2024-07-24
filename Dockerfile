FROM ubuntu:latest
LABEL authors="soyou"

ENTRYPOINT ["top", "-b"]