/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.uv.view.controller.solicitudes.jsf.mb;

import cl.uv.proyecto.persistencia.ejb.FuncionarioDisicoFacadeLocal;
import cl.uv.proyecto.persistencia.entidades.FuncionarioDisico;
import cl.uv.view.controller.base.jsf.mb.MbFuncionarioInfo;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author Alejandro
 */
@ManagedBean
@ViewScoped
public class MbResumenesFuncionarios {

    @EJB
    private FuncionarioDisicoFacadeLocal funcionarioDisicoFacade;
    
    @ManagedProperty(value = "#{mbFuncionarioInfo}")
    private MbFuncionarioInfo mbFuncionarioInfo;
    
    private List<FuncionarioDisico> funcionariosArea;
    private PieChartModel pieModel;
    private String tipoResumen;

    public MbResumenesFuncionarios() {
    }

    @PostConstruct
    private void init() {
        createPieModel();
        funcionariosArea = funcionarioDisicoFacade.buscarFuncrionariosPorArea(mbFuncionarioInfo.getFuncionario().getArea());
    }

    public void setMbFuncionarioInfo(MbFuncionarioInfo mbFuncionarioInfo) {
        this.mbFuncionarioInfo = mbFuncionarioInfo;
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
        pieModel.set("Brand 1", 540);
        pieModel.set("Brand 2", 325);
        pieModel.set("Brand 3", 702);
        pieModel.set("Brand 4", 421);
    }
}
