GRAMMAR TEN STRING GENERATOR
Given a formal grammar in input.txt file, will attempt to generate 10 sample strings from grammar

INPUT.TXT FORMAT
S->g|h|i| ...
T->j|k|l| ...

S/T: Symbol for rule
g/h/i/j/k/l: Generation for path (can be symbols or terminals-mixed)
|: Separator for paths of rule

***First rule is the starting symbol for generation***

LIMITATIONS
Cannot detect generation loops (one rule generating into another consisting of a symbol, only to be generated back to previous)
Limit of strings with length of under 999
Limit of number of rule paths (possible generations) of under 999
