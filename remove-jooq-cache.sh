#!/bin/bash

# shellcheck disable=SC2206
function remove_jooq_cache() {
    declare -a jooqBuildCacheDirs=()

    declare buildCacheDir="./build/classes/java/main/com/mairwunnx/application/"
    declare jooqBuildCacheDir="./build/generated-src/jooq/main/com/mairwunnx/application"

    printf "Removing jooq generated classes cache\n"

    for jooqEntry in "$jooqBuildCacheDir"/*; do
        echo "$jooqEntry added to remove candidate"
        jooqBuildCacheDirs+=("$jooqEntry")
    done

    for buildEntry in "$buildCacheDir"/*; do
        set -f

        local pathParts=(${buildEntry//\// })
        local fileName=${pathParts[-1]}

        printf "\n\nRemoving jooq cache files from gradle class cache\n"

        if [[ ${jooqBuildCacheDirs[*]} =~ ${fileName} ]]; then
            local removeCandidate="$buildCacheDir/$fileName"
            echo "Removing cache file: $removeCandidate"
            rm -rf "$removeCandidate"
        fi
    done

    printf "\n\nRemoving jooq cache file (non-gradle managed class cache)\n"
    rm -rf $jooqBuildCacheDir

    echo "************** Done **************"
}

remove_jooq_cache
