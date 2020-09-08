enscript --highlight=java --color --fancy-header --line-numbers -r -2 --tabsize=2 --indent=2 -o - `find . -name '*.java'` | ps2pdf - files.pdf
