#!/bin/bash

docker run \
       -it \
       -p 8899:8899 \
       --rm \
       --name desktop-to-web-vaadin \
       vaadintutorials/flow-layout-app_layout-vaadin:latest

