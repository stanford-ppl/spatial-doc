# spatial-doc
Documentation repository for spatial

##Editing instructions for changing the documentation website

Directory structure:
```
docs/site   - files for site
docs/source - preprocessed files (cleared before every build)
docs/build  - built html files
src/...     - preprocessor
```

1. Make edits to `docs/site` ONLY - not to `docs/source`
2. Run `make` locally
3. Verify changes using local web browser
4. Commit all changes to `docs/site` and `docs/source`
