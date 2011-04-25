package action.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import metier.Internaute;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import daoHibernate.DAOInternauteHibernate;

import action.form.ActionFormFormuRapide;

public class Connexion extends Action 
{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ActionFormFormuRapide fm = (ActionFormFormuRapide) form;
		
		DAOInternauteHibernate dao = new DAOInternauteHibernate();
		Internaute i = dao.get(fm.getPseudo());
		
		ActionForward a = (ActionForward) (request.getSession().getAttribute("lastPath") != null ? request.getSession().getAttribute("lastPath") : mapping.findForward("index"));
		if (i != null && i.getMdp().equals(fm.getMdp())) 
		{
			request.getSession().setAttribute("pseudo", i);
			
			if (request.getSession().getAttribute("formEncherir") != null)
			{
				request.setAttribute("encherir", request.getSession().getAttribute("formEncherir"));
				request.getSession().removeAttribute("formEncherir");
			}
		}
		else // pas de resultat
		{
			ActionMessages erreur = null;
			erreur = new ActionMessages();
			erreur.add("entete.client.introuvable", new ActionMessage("entete.client.introuvable"));
			this.addErrors(request, erreur);
		}
		
		return a;
	}

}