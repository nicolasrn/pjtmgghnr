package action.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import metier.Internaute;
import metier.Offre;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import dao.DAOOffre;

import action.form.ActionFormFormuIdentiteInternaute;

public class IdentiteInternaute extends Action {
	private DAOOffre daoOffre;
	
	/**
	 * @return the daoOffre
	 */
	public DAOOffre getDaoOffre() {
		return daoOffre;
	}

	/**
	 * @param daoOffre the daoOffre to set
	 */
	public void setDaoOffre(DAOOffre daoOffre) {
		this.daoOffre = daoOffre;
	}

	/* (non-Javadoc)
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionFormFormuIdentiteInternaute f = (ActionFormFormuIdentiteInternaute)form;
		System.out.println(f.getIdOffre());
		Offre o = daoOffre.get(Integer.parseInt(f.getIdOffre()));
		Internaute i = o.getInternaute();
		
		int nbVente = daoOffre.getHistoriqueVente(i).size(),
		nbAchat = daoOffre.getHistoriqueAchat(i).size();
		
		ArrayList<Offre> list = daoOffre.getVenteEncours(i);
		
		request.setAttribute("annonce", list);
		request.setAttribute("internaute", i);
		request.setAttribute("nbVente", nbVente);
		request.setAttribute("nbAchat", nbAchat);
		
		return mapping.findForward("identiteInternaute");
	}

}
