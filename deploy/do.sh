#!/usr/bin/env bash

set -a
if [[ -z "${TS}" ]];
then
    TS="$(touch .version && cat .version)"
    if [[ -z "${TS}" ]]; then
        TS=$(date +%s)
        echo "${TS}" > .version
    fi
else
    echo "${TS}" > .version
fi
DEPLOY_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd )"
BASE_DIR="$( cd ${DEPLOY_DIR} && cd .. && pwd )"
GRADLE=$BASE_DIR/gradlew
APP_VERSION="$( cd $BASE_DIR && $GRADLE | grep GwAuthVersion | awk '/':'/{print $2}' )"
VERSION="${APP_VERSION}.${TS}"
DOCKER=`which docker`
COMPOSE=`which docker-compose`
source ${DEPLOY_DIR}/.env

function do_build(){
    build_jars
    build_web
    build_images
}

function build_jars() {
    echo "Building jars"
    cd ${BASE_DIR} && ${GRADLE} clean auth:bootJar gateway:bootJar resource:bootJar
}



function build_images() {
    echo "Building docker images"
    ${DOCKER} image build $BASE_DIR/auth/build/libs/ --build-arg VERSION=${VERSION} --build-arg APP_VERSION=${APP_VERSION}\
    -t "$DOCKER_REGISTRY/gw-oauth-auth:${VERSION}" -f $DEPLOY_DIR/auth/Dockerfile
    ${DOCKER} image build $BASE_DIR/resource/build/libs/ --build-arg VERSION=${VERSION} --build-arg APP_VERSION=${APP_VERSION}\
    -t "$DOCKER_REGISTRY/gw-oauth-resource:${VERSION}" -f $DEPLOY_DIR/resource/Dockerfile
    ${DOCKER} image build $BASE_DIR/gateway/build/libs/ --build-arg VERSION=${VERSION} --build-arg APP_VERSION=${APP_VERSION}\
    -t "$DOCKER_REGISTRY/gw-oauth-gateway:${VERSION}" -f $DEPLOY_DIR/gateway/Dockerfile
}

function do_start() {
    echo "Starting application"
    ${COMPOSE} -f $DEPLOY_DIR/docker-compose.yml down
    ${COMPOSE} -f $DEPLOY_DIR/docker-compose.yml up -d
}

function do_stop() {
    echo "Stopping application"
    ${COMPOSE} -f $DEPLOY_DIR/docker-compose.yml down
}

function do_pull() {
    echo "Pulling images"
    $COMPOSE -f $DEPLOY_DIR/docker-compose.yml pull
}

function do_ps() {
    echo "Listing containers"
    $COMPOSE -f $DEPLOY_DIR/docker-compose.yml -f $DEPLOY_DIR/docker-compose-taskurotta.yml ps
}


function do_load() {
    do_build
    do_start
}

function do_reload_web() {
    do_stop
    build_web
    do_start
    do_ps
}

function do_logs() {
    ${COMPOSE} -f ${DEPLOY_DIR}/docker-compose.yml logs -f $1
}

function do_version() {
    echo $VERSION
}

cmd=$1
param=$2
case ${cmd}
in
    version) do_version ;;
    load) do_load ;;
    start) do_start ${param};;
    stop) do_stop ;;
    build) do_build ;;
    ps) do_ps ;;
    logs) do_logs ${param};;
    *) echo "Specify command:
    `basename $0` version | load | start | stop | build | ps | logs |
    version - show current version (generated or set)
    load - (re)builds jars, images and (re)starts containers
    start - (re)starts containers
    stop - stops containers
    build - builds jars and images
    ps - lists running containers
    logs - view logs of running containers"
    exit ;;
esac
