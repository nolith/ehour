package net.rrm.ehour.ui.report.panel
package detail


import net.rrm.ehour.report.reports.ReportData
import net.rrm.ehour.ui.common.report.ReportConfig
import net.rrm.ehour.ui.report.panel.AbstractReportPanel
import net.rrm.ehour.ui.report.panel.TreeReportDataPanel
import net.rrm.ehour.ui.report.trend.DetailedReportModel
import net.rrm.ehour.ui.chart.HighChartContainer
import org.apache.wicket.model.Model
import net.rrm.ehour.ui.report.{TreeReportData, TreeReportModel}
import org.apache.wicket.markup.html.WebMarkupContainer
import org.apache.wicket.ajax.AjaxRequestTarget
import net.rrm.ehour.ui.common.component.AjaxBehaviorComponent
import net.rrm.ehour.config.EhourConfig

class DetailedReportPanel(id: String, report: DetailedReportModel) extends AbstractReportPanel(id) {

  setDefaultModel(report)
  setOutputMarkupId(true)

  protected override def onBeforeRender() {
    val frame = new WebMarkupContainer("frame")
    addOrReplace(frame)

    val reportModel = getDefaultModel.asInstanceOf[TreeReportModel]
    frame.add(new TreeReportDataPanel("reportTable", report, ReportConfig.DETAILED_REPORT, DetailedReportExcel.getId))

    val treeReportData: TreeReportData = reportModel.getReportData.asInstanceOf[TreeReportData]
    val rawData: ReportData = treeReportData.getRawReportData
    frame.add(new HighChartContainer("chart", new Model(rawData), DetailedReportChartGenerator.generateHourBasedDetailedChart))

    val radioButton = (id: String, generateChart: (String, ReportData, EhourConfig) => String) => {
      new AjaxBehaviorComponent(id, "onclick", (target: AjaxRequestTarget) => {
        val chart = new HighChartContainer("chart", new Model(rawData), generateChart)
        frame.addOrReplace(chart)
        target.addComponent(chart)
      })
    }

    frame.add(radioButton("turnover", DetailedReportChartGenerator.generateTurnoverBasedDetailedChart))
    frame.add(radioButton("time", DetailedReportChartGenerator.generateHourBasedDetailedChart))

    super.onBeforeRender()
  }
}