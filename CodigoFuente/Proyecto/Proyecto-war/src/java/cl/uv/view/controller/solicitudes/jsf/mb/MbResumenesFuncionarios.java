/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.solicitudes.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.FuncionarioDisicoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.Area;
import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import cl.uv.proyecto.requerimientos.ejb.CalculoDeIndicadoresEJBLocal;
import cl.uv.view.controller.base.jsf.mb.MbFuncionarioInfo;
import cl.uv.view.controller.base.utils.JsfUtils;
import cl.uv.view.controller.base.utils.Resources;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author Alejandro
 */
@ManagedBean
@ViewScoped
public class MbResumenesFuncionarios implements Serializable {
    @EJB
    private CalculoDeIndicadoresEJBLocal calculoDeIndicadoresEJB;

    @EJB
    private FuncionarioDisicoFacadeLocal funcionarioDisicoFacade;
    
    @ManagedProperty(value = "#{mbFuncionarioInfo}")
    private MbFuncionarioInfo mbFuncionarioInfo;
    private List<FuncionarioDisico> funcionariosArea;
    private FuncionarioDisico funcionarioSelected;
    private Area areaSelected;
    private PieChartModel pieModel;
    private String tipoResumen;
    private static final String TIPO_AREA = "area";
    private static final String TIPO_DEPTO = "depto";
    private static final String TIPO_PERSONAL = "personal";
    private Long cantidadSolicitudesPendientes;
    private Long cantidadSolicitudesVencidas;
    private Long cantidadSolicitudesIniciadas;
    private Long cantidadSolicitudesCerradas;
    private Long cantidadSolicitudesEnviadas;
    private Long cantidadSolicitudesRechazadas;
    private Long cantidadSolicitudesTransferida;
    private Long cantidadSolicitudesAsignadas;
    private Long cantidadSolicitudesFinalizadaSinRespuesta;
    private Long totalSolicitudes;

    public MbResumenesFuncionarios() {
    }

    @PostConstruct
    private void init() {
        createPieModel();
        tipoResumen = TIPO_PERSONAL;
        funcionarioSelected = mbFuncionarioInfo.getFuncionario();
        areaSelected = funcionarioSelected.getArea();
        funcionariosArea = funcionarioDisicoFacade.buscarFuncrionariosPorArea(mbFuncionarioInfo.getFuncionario().getArea());
    }

    public void setMbFuncionarioInfo(MbFuncionarioInfo mbFuncionarioInfo) {
        this.mbFuncionarioInfo = mbFuncionarioInfo;
    }

    public FuncionarioDisico getFuncionarioSelected() {
        return funcionarioSelected;
    }

    public void setFuncionarioSelected(FuncionarioDisico funcionarioSelected) {
        this.funcionarioSelected = funcionarioSelected;
    }

    public Area getAreaSelected() {
        return areaSelected;
    }

    public void setAreaSelected(Area areaSelected) {
        this.areaSelected = areaSelected;
    }

    public PieChartModel getPieModel() {
        return pieModel;
    }

    public String getTipoResumen() {
        return tipoResumen;
    }

    public void setTipoResumen(String tipoResumen) {
        this.tipoResumen = tipoResumen;
    }

    public SelectItem[] getItemsAvailableSelectManyNombreCompleto() {
        SelectItem[] listaItems = new SelectItem[funcionariosArea.size()];
        int i = 0;
        for (FuncionarioDisico f : funcionariosArea) {
            listaItems[i++] = new SelectItem(f, f.getNombre() + " " + f.getApellidoPaterno() + " " + f.getApellidoMaterno());
        }
        return listaItems;
    }

    private void createPieModel() {
        pieModel = new PieChartModel();
        pieModel.set("Asignadas", cantidadSolicitudesAsignadas);
        pieModel.set("Cerradas", cantidadSolicitudesCerradas);
        pieModel.set("Finalizada sin Respuesta", cantidadSolicitudesFinalizadaSinRespuesta);
        pieModel.set("Iniciadas", cantidadSolicitudesIniciadas);
        pieModel.set("Pendientes", cantidadSolicitudesPendientes);
        pieModel.set("Rechazada", cantidadSolicitudesRechazadas);
        pieModel.set("Transferida", cantidadSolicitudesTransferida);
        pieModel.set("Vencidas", cantidadSolicitudesVencidas);
    }

    public void onTabChange(TabChangeEvent event) {
        String tab = event.getTab().getTitle();
        if (tab.equals("Area")) {
            tipoResumen = TIPO_AREA;
            areaSelected = mbFuncionarioInfo.getFuncionario().getArea();
        } else if (tab.equals("Departamento")) {
            tipoResumen = TIPO_DEPTO;
        } else { //Personal
            tipoResumen = TIPO_PERSONAL;
            funcionarioSelected = mbFuncionarioInfo.getFuncionario();
        }
        calcularIndicadores();
    }
    
    public void crearResumen(){
        calcularIndicadores();
    }

    private void calcularIndicadores() {
        if (tipoResumen.equals(TIPO_AREA)) {
            calcularIndicadoresArea(areaSelected);
        } else if (tipoResumen.equals(TIPO_DEPTO)) {
            calcularIndicadoresDepto();
        } else if (tipoResumen.equals(TIPO_PERSONAL)) {
            calcularIndicadoresPersonal(funcionarioSelected);
        }
    }

    private void calcularIndicadoresPersonal(FuncionarioDisico f) {
        cantidadSolicitudesCerradas = calculoDeIndicadoresEJB.contarSolicitudes(f, Resources.getValueShort("const", "EstadoSR_CERRADA"));
        cantidadSolicitudesIniciadas = calculoDeIndicadoresEJB.contarSolicitudes(f, Resources.getValueShort("const", "EstadoSR_INICIADA"));
        cantidadSolicitudesPendientes = calculoDeIndicadoresEJB.contarSolicitudes(f, Resources.getValueShort("const", "EstadoSR_PENDIENTE"));
        cantidadSolicitudesVencidas = calculoDeIndicadoresEJB.contarSolicitudes(f, Resources.getValueShort("const", "EstadoSR_VENCIDA"));
        totalSolicitudes = cantidadSolicitudesCerradas + cantidadSolicitudesIniciadas + cantidadSolicitudesPendientes + cantidadSolicitudesVencidas;
    }

    private void calcularIndicadoresArea(Area a) {
        cantidadSolicitudesCerradas = calculoDeIndicadoresEJB.contarSolicitudes(a, Resources.getValueShort("const", "EstadoSR_CERRADA"));
        cantidadSolicitudesIniciadas = calculoDeIndicadoresEJB.contarSolicitudes(a, Resources.getValueShort("const", "EstadoSR_INICIADA"));
        cantidadSolicitudesPendientes = calculoDeIndicadoresEJB.contarSolicitudes(a, Resources.getValueShort("const", "EstadoSR_PENDIENTE"));
        cantidadSolicitudesVencidas = calculoDeIndicadoresEJB.contarSolicitudes(a, Resources.getValueShort("const", "EstadoSR_VENCIDA"));
        cantidadSolicitudesEnviadas = calculoDeIndicadoresEJB.contarSolicitudes(a, Resources.getValueShort("const", "EstadoSR_ENVIADA"));
        cantidadSolicitudesRechazadas = calculoDeIndicadoresEJB.contarSolicitudes(a, Resources.getValueShort("const", "EstadoSR_RECHAZADA"));
        cantidadSolicitudesTransferida = calculoDeIndicadoresEJB.contarSolicitudes(a, Resources.getValueShort("const", "EstadoSR_TRANSFERIDA"));
        cantidadSolicitudesAsignadas = calculoDeIndicadoresEJB.contarSolicitudes(a, Resources.getValueShort("const", "EstadoSR_ASIGNADA"));
        cantidadSolicitudesFinalizadaSinRespuesta = calculoDeIndicadoresEJB.contarSolicitudes(a, Resources.getValueShort("const", "EstadoSR_FINALIZADA_SIN_RESPUESTA"));
        totalSolicitudes = cantidadSolicitudesCerradas + cantidadSolicitudesIniciadas + cantidadSolicitudesPendientes + cantidadSolicitudesVencidas
                           + cantidadSolicitudesEnviadas + cantidadSolicitudesRechazadas + cantidadSolicitudesTransferida + cantidadSolicitudesAsignadas + cantidadSolicitudesFinalizadaSinRespuesta;
    }

    private void calcularIndicadoresDepto() {
        cantidadSolicitudesCerradas = calculoDeIndicadoresEJB.contarSolicitudes(Resources.getValueShort("const", "EstadoSR_CERRADA"));
        cantidadSolicitudesIniciadas = calculoDeIndicadoresEJB.contarSolicitudes(Resources.getValueShort("const", "EstadoSR_INICIADA"));
        cantidadSolicitudesPendientes = calculoDeIndicadoresEJB.contarSolicitudes(Resources.getValueShort("const", "EstadoSR_PENDIENTE"));
        cantidadSolicitudesVencidas = calculoDeIndicadoresEJB.contarSolicitudes(Resources.getValueShort("const", "EstadoSR_VENCIDA"));
        cantidadSolicitudesEnviadas = calculoDeIndicadoresEJB.contarSolicitudes(Resources.getValueShort("const", "EstadoSR_ENVIADA"));
        cantidadSolicitudesRechazadas = calculoDeIndicadoresEJB.contarSolicitudes(Resources.getValueShort("const", "EstadoSR_RECHAZADA"));
        cantidadSolicitudesTransferida = calculoDeIndicadoresEJB.contarSolicitudes(Resources.getValueShort("const", "EstadoSR_TRANSFERIDA"));
        cantidadSolicitudesAsignadas = calculoDeIndicadoresEJB.contarSolicitudes(Resources.getValueShort("const", "EstadoSR_ASIGNADA"));
        cantidadSolicitudesFinalizadaSinRespuesta = calculoDeIndicadoresEJB.contarSolicitudes(Resources.getValueShort("const", "EstadoSR_FINALIZADA_SIN_RESPUESTA"));
        totalSolicitudes = cantidadSolicitudesCerradas + cantidadSolicitudesIniciadas + cantidadSolicitudesPendientes + cantidadSolicitudesVencidas
                           + cantidadSolicitudesEnviadas + cantidadSolicitudesRechazadas + cantidadSolicitudesTransferida + cantidadSolicitudesAsignadas + cantidadSolicitudesFinalizadaSinRespuesta;
    }
}
