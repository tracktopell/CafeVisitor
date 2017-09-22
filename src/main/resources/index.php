<html>
	<head>
	<title>CafeVisitor ${project.version}</title>
	  <style type="text/css">
	  	h1 {
			text-align:center;			
			background-color:#b0e0e6;
		}
		h2 {
			text-align:center;						
		}
		h3 {
			text-align:center;			
			background-color:#b0e0ae;
		}
		.trImg
		{
			padding:15px;
		}
		.tdImg
		{
			padding:15px;
		}
	  </style>
	</head>

	<body>
		<h1>CafeVisitor ${project.version}</h1>
		<h2>Sistema de registro de visitantes a un edificio, con opci&oacute;n de tomar fotograf&iacute;a al visitante e impresi&oacute;n de ticket de pase.</h2			
		<table>
			<tr>
				<td width="60%" valign="top">
						
					<table border="0">
						<tr class="trImg"><td class="tdImg"><img src="imgs/sc1.png" width="100%" height="100%"/></td><td class="tdImg"><img src="imgs/sc2.png" width="100%" height="100%"/></td></tr>
						<tr class="trImg"><td class="tdImg"><img src="imgs/sc3.png" width="100%" height="100%"/></td><td class="tdImg"><img src="imgs/sc4.png" width="100%" height="100%"/></td></tr>
					</table>
					
				</td>
				<td	valign="top">
						<h3>OBJETIVO:</h3>
							<p> 
								CafeVistor es multiplataforma (Windows, MacOS, Linux), se puede instalar en cualqueir sistema que tenga <a href="http://java.com/download">Java 1.6+</a>, 
								Ofrece las funciones de:
							<ul>								
								<li>Registro de datos del visitante.</li>
								<li>Captura la fotografia asociada con el visitante con una webcam.</li>
								<li>Bitadora de registros hist&oacute;rica.</li>
								<li>Imprime un ticket (57 mm. ancho) para entregar al visitante.</li>
								<li>Registro de Salida del visitante, con posibilidad de usar un Escanner de Codigo de barras.</li>								
							</ul>
							</p>
						<h3>DESCARGA:</h3>
						<p>
							Seleccione guardar el <a href="./${project.name}-${project.version}.jar">Instalador (Ver. ${project.version}) Java</a>
							y ejecute en linea de comando: <br/>
							java -jar ${project.name}-${project.version}.jar 
							<br/>
							o si esta bien isntalado su JVM, solo de dobleclick al archivo.<br/>
							Esto debera iniciar el Asistente de instalaci&oacute;n.
						</p>			
						<h3>EMPEZAR A USAR:</h3>  
						<p>		
							Solo inicie y empiece a registrar sus visitantes, recomendado tener una impresora de Tickets termica (min. 57 mm. ancho) y una Webcam ya conectadas.
						</p>
						<h3>ACERCA DE:</h3>  
						<p>		
							Esta es una version de borrador de distribuci&oacute;n de CafeVisitor.
							Mantenida por Alfredo Estrada (tracktopell@gmail.com).
						</p>						
				</td>
			</tr>
		</table>
	</body>
</html>
