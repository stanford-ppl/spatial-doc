12. Compiler Flags 
==================

- sim - Turn on Scala backend
 
- synth - Turn on RTL backend

- instrument - Turn on RTL instrumentation hooks for pipeline balancing anlaysis

- cheapFifos - Use cheap fifos if you know that there are no fifos with lane-dependent enqueues or dequeues

- retime - Turn on RTL retiming to meet higher clock speeds

- syncMem - Turn on synchronous memory operation for all SRAMs to use fewer resources.  Note
  this flag automatically turns on `--retime`

- out - Specify output directory to place generated code.  Default directory is gen/<app name>

- multifile - TBA

- naming - TBA

More to be added...