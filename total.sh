#!/bin/env bash
DIR=$PWD
cd ${0%%/*}

echo "-------------------------------"

files=$(ls Blatt*/result*)

running_reached=0
running_possible=0

for file in $files; do
    points=$(tail -n 1 $file)
    points="${points##*: }"
    points_reached="${points%%/*}"
    points_possible="${points##*/}"

    echo -e " ${file%%/*}: \t$points_reached \t/ $points_possible"

    running_reached=$(($running_reached + $points_reached))
    running_possible=$(($running_possible + $points_possible))
done;

echo "-------------------------------"
echo -e " TOTAL: \t$running_reached \t/ $running_possible"
echo "-------------------------------"

cd $DIR
