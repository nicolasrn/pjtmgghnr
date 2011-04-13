package action.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import metier.Categorie;
import metier.Departement;
import metier.Region;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import daoHibernate.DAOCategorieHibernate;
import daoHibernate.DAODepartementHibernate;
import daoHibernate.DAORegionHibernate;

public class Index extends Action {

	/* (non-Javadoc)
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		request.getSession().setMaxInactiveInterval(Integer.MAX_VALUE);
		
		ArrayList<Departement> listDep = null;
		DAODepartementHibernate dao = new DAODepartementHibernate();
		listDep = dao.loadAll();
		request.getSession().setAttribute("listDepartement", listDep);
		
		ArrayList<Categorie> listCat = null;
		DAOCategorieHibernate daoc = new DAOCategorieHibernate();
		listCat = daoc.loadAll();
		request.getSession().setAttribute("listCategorie", listCat);
		
		ArrayList<Region> listRegion = null;
		DAORegionHibernate daoReg = new DAORegionHibernate();
		listRegion = daoReg.loadAll();
		request.getSession().setAttribute("listRegion", listRegion);
		
		return mapping.findForward("index");
	}

}
