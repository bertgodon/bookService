#! /bin/bash -e

rm -fr build

mkdir build

cp ../target/bookService-0.0.1-SNAPSHOT.jar build

docker build -t bookService ../