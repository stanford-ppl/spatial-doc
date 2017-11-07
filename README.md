# spatial-doc
Documentation repository for spatial

Restructured text is annoying (particularly for tables), so we use a preprocessor to make site maintenance easier.
The preprocessor reads from `docs/site` and generates to `docs/source`
(Naming of `source` is due to the default commands for running sphinx-build on readthedocs)

# Directory structure:
```
docs/site   - files for site
docs/source - preprocessed files (cleared before every build)
docs/build  - built html files
src/...     - preprocessor
```

# Updating Website

1. Make edits to `docs/site` ONLY - not to `docs/source`
2. Run `make` locally
3. Verify changes in docs/build/index.html using local web browser
4. Commit all changes to `docs/site` and `docs/source`
