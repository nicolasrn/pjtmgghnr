package action.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import metier.Encherit;
import metier.Internaute;
import metier.Offre;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import daoHibernate.DAOEncheritHibernate;
import daoHibernate.DAOOffreHibernate;

import action.form.ActionFormFormuEncherir;

public class Encherir extends Action {

	/* (non-Javadoc)
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionFormFormuEncherir f = (ActionFormFormuEncherir)form;
		
		DAOEncheritHibernate dao = new DAOEncheritHibernate();
		DAOOffreHibernate daoOffre = new DAOOffreHibernate();
		
		Offre o = daoOffre.get(Integer.parseInt(f.getIdOffre()));
		Internaute i = (Internaute) request.getSession().getAttribute("pseudo");
		request.getSession().setAttribute("id", o.getIdOffre());
		System.out.println("dans encherir : " + request.getSession().getAttribute("id"));
		if (i == null)
		{
			request.getSession().setAttribute("formEncherir", request.getAttribute("encherir"));
			
			ActionForward a = mapping.getInputForward();
			request.getSession().setAttribute("lastPath", a);
			
			ActionMessages erreur = new ActionMessages();
			erreur.add("entete.connexion", new ActionMessage("entete.connexion"));
			this.addErrors(request, erreur);
			
			return mapping.getInputForward();
		}
		
		Date timestamp = new Timestamp(new Date().getTime());
		System.out.println(timestamp);
		Encherit e = new Encherit(Double.parseDouble(f.getEnchere()), timestamp, (Internaute)request.getSession().getAttribute("pseudo"), o);
		
		ArrayList<Encherit> s = dao.get(o);
		double ss = Double.POSITIVE_INFINITY, tmp = Double.NEGATIVE_INFINITY;
		for(Encherit encherit : s)
			if (encherit.getPrix() > tmp)
				tmp = encherit.getPrix();
		ss = tmp;
		
		System.out.println(f.getEnchere() + " sur " + f.getIdOffre());
		
		if (i != null)
		{
			if ((s == null && e.getPrix() > o.getMiseAPrix()) || (s != null && e.getPrix() > ss))
			{
				dao.saveOrUpdate(e);
			}
			else
			{
				ActionMessages erreur = new ActionMessages();
				erreur.add("enchere.insuffisant", new ActionMessage("enchere.insuffisant"));
				this.addErrors(request, erreur);
			}
		}
		return mapping.getInputForward();
	}
}