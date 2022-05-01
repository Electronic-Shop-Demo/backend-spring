#!/bin/bash

function remove_directories() {
    printf "Removing .gradle, build directories"

    rm -rf "./.gradle"
    rm -rf "./build"
}

remove_directories
