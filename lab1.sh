#!/bin/sh

#PBS -N LAB_1
#PBS -q bitpedu
#PBS -o lab1.out
#PBS -e lab1.err
#PBS -l walltime=00:01:00

cd $PBS_WORKDIR

java Main 
