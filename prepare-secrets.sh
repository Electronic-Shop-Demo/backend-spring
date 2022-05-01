#!/bin/bash

printf "Preparing secrets for backend-spring project\n"
printf "Moving secret file candidates to ./src/main/resources/keys/\n"

declare gitignore=".gitignore"
declare -a src_file_names=()
declare -a des_file_names=()

function receive_secret_file_names() {
    src_file_names=(
        "../misc-secrets/keystore-jwt.jks"
        "../misc-secrets/keystore-jwt-refresh.jks"
    )

    des_file_names=(
        "./src/main/resources/keys/keystore-jwt.jks"
        "./src/main/resources/keys/keystore-jwt-refresh.jks"
    )
}

# shellcheck disable=SC2206
function create_not_exist_directories() {
    printf "\nEnsuring directories exists...\n"

    for val in "${des_file_names[@]}"; do # "./src/main/resources/keys/keystore-jwt.jks"
        set -f                            # avoid globbing (expansion of *).
        local pathParts=(${val//\// })    # [.,src,main,resources,keys,keystore-jwt.jks]

        unset 'pathParts[-1]' # [.,src,main,resources,keys]

        for i in "${pathParts[@]}"; do
            directoryPath+="$i/"
        done

        mkdir -p "$directoryPath"

        echo "Directory with path: $directoryPath created"

        directoryPath=""
    done
}

function copy_secret_files() {
    printf "\nMoving files (source --> destination):\n"

    for i in "${!src_file_names[@]}"; do
        local file_name="${des_file_names[i]}"
        local src_file_name="${src_file_names[i]}"
        echo "    > $src_file_name --> $file_name"
        cp -i "$src_file_name" "$file_name"
    done
}

# shellcheck disable=SC2206
function add_files_to_git_ignore() {
    printf "\nAdding secret files to gitignore automatically...\n"

    for val in "${des_file_names[@]}"; do # "./src/main/resources/keys/keystore-jwt.jks"
        set -f                            # avoid globbing (expansion of *).
        local pathParts=(${val//\// })    # [.,src,main,resources,keys,keystore-jwt.jks]
        local fileName=${pathParts[-1]}   # [keystore-jwt.jks]

        if ! grep -q "$fileName" $gitignore; then
            echo "$fileName" >>$gitignore
            echo "    > File $fileName added to gitignore file!"
        else
            echo "    > File $fileName no need to add to gitignore file (already defined)"
        fi
    done
}

receive_secret_file_names
create_not_exist_directories
copy_secret_files
add_files_to_git_ignore
