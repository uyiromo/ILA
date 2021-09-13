# ILA
This repository contains the Chisel wrapper for Xilinx ILA (Integrated Logic Analyzer).
Codes in this repository is written upon an assumption that it is used as submodule of [freedom (SiFive)](https://github.com/sifive/freedom) or [freedom (uyiromo)](https://github.com/uyiromo/freedom).

## Usage
1. Add the `ILA` project to build.sbt
```scala
lazy val ila = (project in file("ILA")).
  dependsOn(rocketChip).
  settings(commonSettings: _*)
```

2. Add dependency to project
```scala
// Example: to use ILA in srcs in freedomPlatforms
lazy val freedomPlatforms = (project in file(".")).
  dependsOn(rocketChip, sifiveBlocks, sifiveCache, nvdlaBlocks, openmpe, fpgaShells).
  settings(commonSettings: _*)
```

3. Instantiate ILA
```scala
// Example
val ila = ILA(clock, Seq(
    io.fe.master.bits.addr,
    memLevel,
    cwmacLevel,
    treeState,
    io.be.master.bits.addr,
    io.be.slave.bits.data,
    io.root,
    cwmacTagsEqual,
    verified,
    io.fe.slave.bits.resp
  ))
```

## Details
1. The first argument is `clock` signal, target signals are sampled for each it.
2. The second- arguments are sample signals. The ILA class can accept `32` signals at the maximum.
3. Sampling depth is defined by `CONFIG.C_DATA_DEPTH {1024}` in `ILA.scala`.
  At default, ILA records signals for `1024` clocks.
  If you want to take more samples, increase `CONFIG.C_DATA_DEPTH`.
  **NOTICE**: More depth requires more BRAM, and increases difficulty in placing your design.


















