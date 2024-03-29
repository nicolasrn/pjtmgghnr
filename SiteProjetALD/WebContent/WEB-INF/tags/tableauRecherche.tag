<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html"%>
<%@ taglib prefix="bean" uri="http://struts.apache.org/tags-bean"%>
<%@ taglib prefix="logic" uri="http://struts.apache.org/tags-logic"%>

<%@attribute name="nom" required="true"%>
<%@attribute name="nomAttribute" required="true"%>

${pageScope.nom}
<table border="1">
	<tr>
		<th></th>
		<th><bean:message key="tableau.nom"/></th>
		<th><bean:message key="tableau.date"/></th>
		<th><bean:message key="tableau.prix"/></th>
		<logic:present name="affi">
			<th></th>
		</logic:present>
	</tr>
	<logic:iterate id="list" name="${pageScope.nomAttribute}" >
	<tr>
		<td>
			<logic:iterate id="img" name="list" property="image" length="1">
				<img src="<bean:write name="img" property="libelleImage"/>" alt="<bean:write name="img" property="libelleImage"/>" width="50" height="50" />
			</logic:iterate>
		</td>
		<td>
			<html:link action="/identiteAnnonce.do?id=${list.idOffre}">
				<bean:write name="list" property="titre" />
			</html:link>
		</td><!-- il faut ajouter un lien vers le descriptif de l'article -->
		<td><bean:write name="list" property="dateFin" format="EEEEE dd MMMM yyyy"/></td>
		<%--<td><bean:write name="list" property="dateDepot" format="EEEEE dd MMMM yyyy HH:mm:ss"/></td> --%>
		<td><bean:write name="list" property="miseAPrix" /></td>
		
		<logic:present name="affi">
			<td>
				<logic:equal value="false" name="list" property="valable"><bean:message key="${affi}"/></logic:equal>
			</td>
		</logic:present>
	</tr>
	</logic:iterate>
</table>