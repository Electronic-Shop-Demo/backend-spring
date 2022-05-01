#!/bin/bash

# shellcheck disable=SC2206
function remove_directories() {
    declare -a jooqBuildCacheDirs=()

    declare buildCacheDir="./build/classes/java/main/com/mairwunnx/application/"
    declare jooqBuildCacheDir="./build/generated-src/jooq/main/com/mairwunnx/application"

    printf "Removing jooq generated classes cache\n"

    for jooqEntry in "$jooqBuildCacheDir"/*; do
        echo "$jooqEntry"
        jooqBuildCacheDirs+=("$jooqEntry")
    done

    for buildEntry in "$buildCacheDir"/*; do
        set -f

        local pathParts=(${buildEntry//\// })
        local fileName=${pathParts[-1]}

        if [[ ${jooqBuildCacheDirs[*]} =~ ${fileName} ]]; then
            local removeCandidate="$buildCacheDir/$fileName"
            rm -rf "$removeCandidate"
        fi
    done

    rm -rf $jooqBuildCacheDir
}

remove_directories
