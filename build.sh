#!/bin/bash
docker build -t vaadintutorials/flow-layout-app_layout-vaadin .
#docker tag vaadintutorials/flow-layout-app_layout-vaadin:latest vaadintutorials/flow-layout-app_layout-vaadin:20190826-001
#docker push vaadintutorials/flow-layout-app_layout-vaadin:20190826-001
docker push vaadintutorials/flow-layout-app_layout-vaadin:latest
