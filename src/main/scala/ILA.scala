package nvsit

import Chisel._
import chisel3.util._
import freechips.rocketchip.util.{ElaborationArtefacts}
import freechips.rocketchip.util.GenericParameterizedBundle
import freechips.rocketchip.config._
import chisel3.core.dontTouch

//-------------------------------------------------------------------------
// ILA (Integrated Logic Analyzer)
//-------------------------------------------------------------------------
class ILA[T <: Data](clk: Clock, ports: Seq[T], name: String) extends BlackBox
{
  val io = new Bundle {
    val clk = Input(Clock())
    val probe0  = if (ports.size >=  1) Some(Input(UInt(ports(0).getWidth))) else None
    val probe1  = if (ports.size >=  2) Some(Input(UInt(ports(1).getWidth))) else None
    val probe2  = if (ports.size >=  3) Some(Input(UInt(ports(2).getWidth))) else None
    val probe3  = if (ports.size >=  4) Some(Input(UInt(ports(3).getWidth))) else None
    val probe4  = if (ports.size >=  5) Some(Input(UInt(ports(4).getWidth))) else None
    val probe5  = if (ports.size >=  6) Some(Input(UInt(ports(5).getWidth))) else None
    val probe6  = if (ports.size >=  7) Some(Input(UInt(ports(6).getWidth))) else None
    val probe7  = if (ports.size >=  8) Some(Input(UInt(ports(7).getWidth))) else None
    val probe8  = if (ports.size >=  9) Some(Input(UInt(ports(8).getWidth))) else None
    val probe9  = if (ports.size >= 10) Some(Input(UInt(ports(9).getWidth))) else None
    val probe10 = if (ports.size >= 11) Some(Input(UInt(ports(10).getWidth))) else None
    val probe11 = if (ports.size >= 12) Some(Input(UInt(ports(11).getWidth))) else None
    val probe12 = if (ports.size >= 13) Some(Input(UInt(ports(12).getWidth))) else None
    val probe13 = if (ports.size >= 14) Some(Input(UInt(ports(13).getWidth))) else None
    val probe14 = if (ports.size >= 15) Some(Input(UInt(ports(14).getWidth))) else None
    val probe15 = if (ports.size >= 16) Some(Input(UInt(ports(15).getWidth))) else None
    val probe16 = if (ports.size >= 17) Some(Input(UInt(ports(16).getWidth))) else None
    val probe17 = if (ports.size >= 18) Some(Input(UInt(ports(17).getWidth))) else None
    val probe18 = if (ports.size >= 19) Some(Input(UInt(ports(18).getWidth))) else None
    val probe19 = if (ports.size >= 20) Some(Input(UInt(ports(19).getWidth))) else None
    val probe20 = if (ports.size >= 21) Some(Input(UInt(ports(20).getWidth))) else None
    val probe21 = if (ports.size >= 22) Some(Input(UInt(ports(21).getWidth))) else None
    val probe22 = if (ports.size >= 23) Some(Input(UInt(ports(22).getWidth))) else None
    val probe23 = if (ports.size >= 24) Some(Input(UInt(ports(23).getWidth))) else None
    val probe24 = if (ports.size >= 25) Some(Input(UInt(ports(24).getWidth))) else None
    val probe25 = if (ports.size >= 26) Some(Input(UInt(ports(25).getWidth))) else None
    val probe26 = if (ports.size >= 27) Some(Input(UInt(ports(26).getWidth))) else None
    val probe27 = if (ports.size >= 28) Some(Input(UInt(ports(27).getWidth))) else None
    val probe28 = if (ports.size >= 29) Some(Input(UInt(ports(28).getWidth))) else None
    val probe29 = if (ports.size >= 30) Some(Input(UInt(ports(29).getWidth))) else None
    val probe30 = if (ports.size >= 31) Some(Input(UInt(ports(30).getWidth))) else None
    val probe31 = if (ports.size >= 32) Some(Input(UInt(ports(31).getWidth))) else None
  }

  val moduleName = name
  override def desiredName = moduleName

  // Generate C_PROBEX_WIDTH
  val probeWidth = ports.zipWithIndex.map { case (p, i) =>
    s"CONFIG.C_PROBE${i}_WIDTH {${p.getWidth}}"
  }.mkString(" \\\n")

  ElaborationArtefacts.add(s"${moduleName}.vivado.tcl",
    s"""create_ip -name ila -vendor xilinx.com -library ip -module_name \\
     | ${moduleName} -dir $$ipdir -force
     |set_property -dict [list \\
     | CONFIG.C_DATA_DEPTH {1024} \\
     | ${probeWidth} \\
     | CONFIG.C_NUM_OF_PROBES {${ports.size}} \\
     | CONFIG.C_EN_STRG_QUAL {1} \\
     | CONFIG.C_INPUT_PIPE_STAGES {2} \\
     | CONFIG.C_ADV_TRIGGER {true} \\
     | CONFIG.ALL_PROBE_SAME_MU_CNT {4} \\
     |] [get_ips ${moduleName}]
     |""".stripMargin)
}

object ILA {
  var ilaIndex = 0

  def apply[T <: Data] (clk: Clock, ports: Seq[T]): Unit = {
    val name = s"ila_${ilaIndex}"
    ilaIndex = ilaIndex + 1

    val ila = Module(new ILA(clk, ports, name))
    ila.io.clk := clk
    if (ports.size >=  1) { ila.io.probe0.get  := ports(0) }
    if (ports.size >=  2) { ila.io.probe1.get  := ports(1) }
    if (ports.size >=  3) { ila.io.probe2.get  := ports(2) }
    if (ports.size >=  4) { ila.io.probe3.get  := ports(3) }
    if (ports.size >=  5) { ila.io.probe4.get  := ports(4) }
    if (ports.size >=  6) { ila.io.probe5.get  := ports(5) }
    if (ports.size >=  7) { ila.io.probe6.get  := ports(6) }
    if (ports.size >=  8) { ila.io.probe7.get  := ports(7) }
    if (ports.size >=  9) { ila.io.probe8.get  := ports(8) }
    if (ports.size >= 10) { ila.io.probe9.get  := ports(9) }
    if (ports.size >= 11) { ila.io.probe0.get  := ports(10) }
    if (ports.size >= 12) { ila.io.probe11.get := ports(11) }
    if (ports.size >= 13) { ila.io.probe12.get := ports(12) }
    if (ports.size >= 14) { ila.io.probe13.get := ports(13) }
    if (ports.size >= 15) { ila.io.probe14.get := ports(14) }
    if (ports.size >= 16) { ila.io.probe15.get := ports(15) }
    if (ports.size >= 17) { ila.io.probe16.get := ports(16) }
    if (ports.size >= 18) { ila.io.probe17.get := ports(17) }
    if (ports.size >= 19) { ila.io.probe18.get := ports(18) }
    if (ports.size >= 20) { ila.io.probe19.get := ports(19) }
    if (ports.size >= 21) { ila.io.probe20.get := ports(20) }
    if (ports.size >= 22) { ila.io.probe21.get := ports(21) }
    if (ports.size >= 23) { ila.io.probe22.get := ports(22) }
    if (ports.size >= 24) { ila.io.probe23.get := ports(23) }
    if (ports.size >= 25) { ila.io.probe24.get := ports(24) }
    if (ports.size >= 26) { ila.io.probe25.get := ports(25) }
    if (ports.size >= 27) { ila.io.probe26.get := ports(26) }
    if (ports.size >= 28) { ila.io.probe27.get := ports(27) }
    if (ports.size >= 29) { ila.io.probe28.get := ports(28) }
    if (ports.size >= 30) { ila.io.probe29.get := ports(29) }
    if (ports.size >= 31) { ila.io.probe30.get := ports(30) }
    if (ports.size >= 32) { ila.io.probe31.get := ports(31) }
  }
}
