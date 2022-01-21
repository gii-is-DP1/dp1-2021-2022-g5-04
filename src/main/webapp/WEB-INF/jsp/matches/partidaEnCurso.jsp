<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="IdusMartii" tagdir="/WEB-INF/tags"%>
<c:if test="${admin}">
	<IdusMartii:adminLayout pageName="matches">
		<a href="http://localhost:8080/chats/${match.id}" target="_blank">
			<button class ="btn btn-primary" style="margin-bottom: 10px;">CHAT</button>
		</a>
		<table class="table table-striped">
			<tr>
				<th>Nombre</th>
				<td><b><c:out value="${match.name} " /></b></td>
			</tr>
			<tr>
				<th>Turno</th>
				<td><c:out value="${match.turn}" /></td>
			</tr>
			<tr>
				<th>Ronda</th>
				<td><c:out value="${match.round}" /></td>
			</tr>
			<tr>
				<th>Votos a favor</th>
				<td><c:out value="${match.votesInFavor}" /></td>
			</tr>
			<tr>
				<th>Votos en Contra</th>
				<td><c:out value="${match.votesAgainst}" /></td>
			</tr>
			<tr>
				<th>Creador</th>
				<td><c:out value="${match.players[0].user.username}" /></td>
			</tr>
			<tr>
				<th>Jugador</th>
				<td><c:forEach var="x" items="${match.players}">
						<li>${x.user.username}---> ${x.role}</li>

					</c:forEach></td>
			</tr>

		</table>
		
			<c:if test="${votoAmarilloRevisado}">
				<div class="row" >
					<div class="col-2" style="background-color: yellow;">

						<h3>El jugador ${playerY.user.username} ha votado nulo</h3>
					</div>
				</div>
			</c:if>
		
		<c:forEach items="${match.players}" var="player">
			<div class="row">
				<c:if test="${player.user.username eq current}">
					<div class="col-md-2">
						<h2>Tu rol actual es:</h2>
						<img class="img-responsive" src="/resources/images/${roleCard}.jpg" style="height: 200px; width: 150px;"/>
					</div>
					<div class="col-sm-3">
						<c:if test="${ronda2}">
							<c:if test="${elegirFaccion}">
								<p>Tienes que elegir una de tus cartas de faccion. Despues se contaran los votos.</p>
							</c:if>
							<c:if test="${elegirRol}">
								<c:forEach var="pla" items="${jugadoresSinRolConsul}">
									<c:if test="${edilesSinAsignar}">
										<c:if test="${numeroJugadoresCinco}">
											<form:form modelAttribute="match" class="form-horizontal" id="add-match-form" action="/players/${pla.id}/${id}/asignarEdil">
												<div class="col">
													<button class="btn btn-success" type="submit">Asignar a ${pla.user.username} EDIL</button>
												</div>
											</form:form>
										</c:if>
										<c:if test="${numeroJugadoresCinco eq false}">
											<c:if test="${pla.role != 'EDIL'}">
												<form:form modelAttribute="match" class="form-horizontal" id="add-match-form" action="/players/${pla.id}/${id}/asignarEdil">
													<div class="col">
														<button class="btn btn-success" type="submit">Asignar a ${pla.user.username} EDIL</button>
													</div>
												</form:form>
											</c:if>
										</c:if>
									</c:if>
									<c:if test="${pretorSinAsignar}">
										<c:if test="${pla.role != 'PRETOR'}">
											<form:form modelAttribute="match" class="form-horizontal" id="add-match-form" action="/players/${pla.id}/${id}/asignarPretor">
												<div class="col">
													<button class="btn btn-warning" type="submit">Asignar a ${pla.user.username} PRETOR</button>
												</div>
											</form:form>
										</c:if>
									</c:if>
								</c:forEach>
							</c:if>
							<c:if test="${contarVotos}">
								<form:form modelAttribute="match" class="form-horizontal" id="add-match-form" action="/players/${id}/NuevoTurno">
									<div class="form-group has-feedback"></div>
									<div class="form-group">
										<div class="col">
											<button class="btn btn-warning" type="submit">Contar Votos</button>
										</div>
									</div>
								</form:form>
							</c:if>
							<c:if test="${votar}">
								<div class="col-sm-offset-2 col-sm-10">
									<form:form modelAttribute="player_actual" class="form-horizontal" id="add-match-form" action="/players/${player.id}/${id}/${votos[0]}">
										<div class="form-group">
											<div class="col">
												<button class="btn btn-success" type="submit">Votar a favor</button>
											</div>
										</div>
									</form:form>
									<form:form modelAttribute="player_actual" class="form-horizontal" id="add-match-form" action="/players/${player.id}/${id}/${votos[1]}">
										<div class="form-group">
											<div class="col">
												<button class="btn btn-danger" type="submit">Votar en contra</button>
											</div>
										</div>
									</form:form>
									<c:if test="${!votoAmarilloRevisado}">
										<form:form modelAttribute="player_actual" class="form-horizontal" id="add-match-form" action="/players/${player.id}/${id}/${votos[2]}">
											<div class="form-group">
												<div class="col">
													<button class="btn btn-warning" type="submit">Votar nulo</button>
												</div>
											</div>
										</form:form>
									</c:if>
								</div>
							</c:if>
							<c:if test="${revisarVoto}">
								<c:forEach var="edil" items="${ediles}">
									<form:form modelAttribute="match" class="form-horizontal" id="add-match-form" action="/players/${edil.id}/${id}/revisar" method="GET">
										<div class="form-group has-feedback"></div>
										<div class="form-group">
											<div class="col">
												<button class="btn btn-warning" type="submit">Revisar voto de ${edil.user.username}</button>
											</div>
										</div>
									</form:form>
								</c:forEach>
							</c:if>
						</c:if>


						<c:if test="${ronda1}">
							<c:if test="${votar}">
								<div class="col-sm-10">
									<form:form modelAttribute="player_actual" class="form-horizontal"
										id="add-match-form"
										action="/players/${player.id}/${id}/${votos[0]}">
										<div class="form-group">
											<div class="col">
												<button class="btn btn-success" type="submit">Votar a
													favor</button>
											</div>
										</div>
									</form:form>
									<form:form modelAttribute="player_actual" class="form-horizontal"
										id="add-match-form"
										action="/players/${player.id}/${id}/${votos[1]}">
										<div class="form-group">
											<div class="col">
												<button class="btn btn-danger" type="submit">Votar
													en contra</button>
											</div>
										</div>
									</form:form>
								</div>
							</c:if>
							<c:if test="${revisarVoto}">
								<c:forEach var="ed" items="${ediles}">
									<form:form modelAttribute="match" class="form-horizontal"
										id="add-match-form" action="/players/${ed.id}/${id}/revisar"
										method="GET">
										<div class="form-group has-feedback"></div>
										<div class="form-group">
											<div class="col">
												<button class="btn btn-warning" type="submit">Revisar
													voto de ${ed.user.username}</button>
											</div>
										</div>
									</form:form>
								</c:forEach>
							</c:if>
							<c:if test="${elegirFaccion}">
								<p>Tienes que elegir una de tus cartas de faccion. Despues se contaran los votos.</p>
							</c:if>
							<c:if test="${contarVotos}">
								
								<form:form modelAttribute="match" class="form-horizontal"
									id="add-match-form" action="/players/${id}/NuevoTurno">
									<div class="form-group has-feedback"></div>
									<div class="form-group">
										<div class="col">
											<button class="btn btn-warning" type="submit">Contar
												Votos</button>
										</div>
									</div>
								</form:form>
							</c:if>
						</c:if>
						</div>
						
					
						<c:if test="${mostrarCartas}">
						
								<c:if test="${!elegirFaccion}">
									<div class="col-md-4">
										<h2>Tus cartas de faccion son: </h2>
										<div class="col-md-6">
											<img class="img-responsive" src="/resources/images/${card1}.jpg" style="height: 200px; width: 150px;"/>
										</div>
										<div class="col-md-6">
											<img class="img-responsive" src="/resources/images/${card2}.jpg" style="height: 200px; width: 150px;"/>
										</div>
									</div>
								</c:if>
									
							
							
								<c:if test="${elegirFaccion}">
									<div class="col-md-4">
										<h2>Tus cartas de faccion son: </h2>
										<form:form modelAttribute="match" class="form-horizontal"
											id="add-match-form"
											action="/players/${player.id}/${id}/${player.card1}/ElegirCartaFaccion1">
												<div class="col-md-6">
													<button type="submit"><img class="img-responsive" src="/resources/images/${card1}.jpg" style="height: 200px; width: 150px;"/></button>
												</div>
											
										</form:form>
										<form:form modelAttribute="match" class="form-horizontal"
											id="add-match-form"
											action="/players/${player.id}/${id}/${player.card2}/ElegirCartaFaccion2">
											
												<div class="col-md-6">
													<button type="submit"><img class="img-responsive" src="/resources/images/${card2}.jpg" style="height: 200px; width: 150px;"/></button>
												</div>
											
										</form:form>
									</div>
								</c:if>
							
						
					</c:if>
					<c:if test="${mostrarCartas eq false}">
						<div class="col-md-4">
							<h2>Tu faccion es:</h2>
							<div class="col-md-6">
								<img class="img-responsive" src="/resources/images/${card1}.jpg" style="height: 200px; width: 150px;"/>
							</div>
						</div>
					</c:if>
					<c:if test="${voteCondition}">
						<div class="col-md-3">
							<h2>Tu voto: </h2>
							<img class="img-responsive" src="/resources/images/${voteCard}.jpg" style="height: 200px; width: 150px;"/>
						</div>
					</c:if>
					
				
			</c:if>
		</c:forEach>
	</IdusMartii:adminLayout>
</c:if>
<c:if test="${admin eq false}">
	<IdusMartii:layout pageName="matches">
		<a href="http://localhost:8080/chats/${match.id}" target="_blank">
			<button class ="btn btn-primary" style="margin-bottom: 10px;">CHAT</button>
		</a>
		<table class="table table-striped">
			<tr>
				<th>Nombre</th>
				<td><b><c:out value="${match.name} " /></b></td>
			</tr>
			<tr>
				<th>Turno</th>
				<td><c:out value="${match.turn}" /></td>
			</tr>
			<tr>
				<th>Ronda</th>
				<td><c:out value="${match.round}" /></td>
			</tr>
			<tr>
				<th>Votos a favor</th>
				<td><c:out value="${match.votesInFavor}" /></td>
			</tr>
			<tr>
				<th>Votos en Contra</th>
				<td><c:out value="${match.votesAgainst}" /></td>
			</tr>
			<tr>
				<th>Creador</th>
				<td><c:out value="${match.players[0].user.username}" /></td>
			</tr>
			<tr>
				<th>Jugadores</th>
				<td><c:forEach var="x" items="${match.players}">
						<li>${x.user.username}---> ${x.role}</li>

					</c:forEach></td>
			</tr>

		</table>
		
			<c:if test="${votoAmarilloRevisado}">
				<div class="row" >
					<div class="col-2" style="background-color: yellow;">

						<h3>El jugador ${playerY.user.username} ha votado nulo</h3>
					</div>
				</div>
			</c:if>
		
		<c:forEach items="${match.players}" var="player">
			<div class="row">
				<c:if test="${player.user.username eq current}">
					
						<div class="col-md-2">
					
							<h2>Tu rol actual es:</h2>
						
							<img class="img-responsive" src="/resources/images/${roleCard}.jpg" style="height: 200px; width: 150px;"/>
						</div>
						<div class="col-sm-3">
								<c:if test="${ronda2}">
									<c:if test="${elegirFaccion}">
										<p>Tienes que elegir una de tus cartas de faccion. Despues se contaran los votos.</p>
									</c:if>
									<c:if test="${elegirRol}">
										<c:forEach var="pla" items="${jugadoresSinRolConsul}">
											<c:if test="${edilesSinAsignar}">
												<c:if test="${numeroJugadoresCinco}">
													<form:form modelAttribute="match" class="form-horizontal"
														id="add-match-form"
														action="/players/${pla.id}/${id}/asignarEdil">
															<div class="col">
																<button class="btn btn-success" type="submit">Asignar
																	a ${pla.user.username} EDIL</button>
															</div>
													</form:form>
												</c:if>
												<c:if test="${numeroJugadoresCinco eq false}">
													<c:if test="${pla.role != 'EDIL'}">
														<form:form modelAttribute="match" class="form-horizontal"
															id="add-match-form"
															action="/players/${pla.id}/${id}/asignarEdil">
														
														
																<div class="col">
																	<button class="btn btn-success" type="submit">Asignar
																		a ${pla.user.username} EDIL</button>
																</div>
														
														</form:form>
													</c:if>
												</c:if>
											</c:if>
											<c:if test="${pretorSinAsignar}">
												<c:if test="${pla.role != 'PRETOR'}">
													<form:form modelAttribute="match" class="form-horizontal"
														id="add-match-form"
														action="/players/${pla.id}/${id}/asignarPretor">
														
														
															<div class="col">
																<button class="btn btn-warning" type="submit">Asignar
																	a ${pla.user.username} PRETOR</button>
															</div>
														
													</form:form>
												</c:if>
											</c:if>
										</c:forEach>
									</c:if>
									<c:if test="${contarVotos}">
											<form:form modelAttribute="match" class="form-horizontal"
												id="add-match-form" action="/players/${id}/NuevoTurno">
												<div class="form-group has-feedback"></div>
												<div class="form-group">
													<div class="col">
														<button class="btn btn-warning" type="submit">Contar
															Votos</button>
													</div>
												</div>
											</form:form>
									</c:if>

									<c:if test="${votar}">
										<div class="col-sm-offset-2 col-sm-10">
											<form:form modelAttribute="player_actual" class="form-horizontal"
												id="add-match-form"
												action="/players/${player.id}/${id}/${votos[0]}">
												<div class="form-group">
													<div class="col">
														<button class="btn btn-success" type="submit">Votar a
															favor</button>
													</div>
												</div>
											</form:form>
											<form:form modelAttribute="player_actual" class="form-horizontal"
												id="add-match-form"
												action="/players/${player.id}/${id}/${votos[1]}">
												<div class="form-group">
													<div class="col">
														<button class="btn btn-danger" type="submit">Votar
															en contra</button>
													</div>
												</div>
											</form:form>
											<c:if test="${!votoAmarilloRevisado}">
												<form:form modelAttribute="player_actual"
													class="form-horizontal" id="add-match-form"
													action="/players/${player.id}/${id}/${votos[2]}">
													<div class="form-group">
														<div class="col">
															<button class="btn btn-warning" type="submit">Votar
																nulo</button>
														</div>
													</div>
												</form:form>
											</c:if>

										</div>
									</c:if>
									<c:if test="${revisarVoto}">
										<c:forEach var="edil" items="${ediles}">
											<form:form modelAttribute="match" class="form-horizontal"
												id="add-match-form" action="/players/${edil.id}/${id}/revisar"
												method="GET">
												<div class="form-group has-feedback"></div>
												<div class="form-group">
													<div class="col">
														<button class="btn btn-warning" type="submit">Revisar
															voto de ${edil.user.username}</button>
													</div>
												</div>
											</form:form>
										</c:forEach>
									</c:if>
								</c:if>







							<c:if test="${ronda1}">
								<c:if test="${votar}">
									<div class="col-sm-10">
										<form:form modelAttribute="player_actual" class="form-horizontal"
											id="add-match-form"
											action="/players/${player.id}/${id}/${votos[0]}">
											<div class="form-group">
												<div class="col">
													<button class="btn btn-success" type="submit">Votar a
														favor</button>
												</div>
											</div>
										</form:form>
										<form:form modelAttribute="player_actual" class="form-horizontal"
											id="add-match-form"
											action="/players/${player.id}/${id}/${votos[1]}">
											<div class="form-group">
												<div class="col">
													<button class="btn btn-danger" type="submit">Votar
														en contra</button>
												</div>
											</div>
										</form:form>
									</div>
								</c:if>
								<c:if test="${revisarVoto}">
									<c:forEach var="ed" items="${ediles}">
										<form:form modelAttribute="match" class="form-horizontal"
											id="add-match-form" action="/players/${ed.id}/${id}/revisar"
											method="GET">
											<div class="form-group has-feedback"></div>
											<div class="form-group">
												<div class="col">
													<button class="btn btn-warning" type="submit">Revisar
														voto de ${ed.user.username}</button>
												</div>
											</div>
										</form:form>
									</c:forEach>
								</c:if>
								<c:if test="${elegirFaccion}">
									<p>Tienes que elegir una de tus cartas de faccion. Despues se contaran los votos.</p>
								</c:if>
								<c:if test="${contarVotos}">
									
									<form:form modelAttribute="match" class="form-horizontal"
										id="add-match-form" action="/players/${id}/NuevoTurno">
										<div class="form-group has-feedback"></div>
										<div class="form-group">
											<div class="col">
												<button class="btn btn-warning" type="submit">Contar
													Votos</button>
											</div>
										</div>
									</form:form>
								</c:if>
							</c:if>
						</div>
						
					
						<c:if test="${mostrarCartas}">
						
								<c:if test="${!elegirFaccion}">
									<div class="col-md-4">
										<h2>Tus cartas de faccion son: </h2>
										<div class="col-md-6">
											<img class="img-responsive" src="/resources/images/${card1}.jpg" style="height: 200px; width: 150px;"/>
										</div>
										<div class="col-md-6">
											<img class="img-responsive" src="/resources/images/${card2}.jpg" style="height: 200px; width: 150px;"/>
										</div>
									</div>
								</c:if>
									
							
							
								<c:if test="${elegirFaccion}">
									<div class="col-md-4">
										<h2>Tus cartas de faccion son: </h2>
										<form:form modelAttribute="match" class="form-horizontal"
											id="add-match-form"
											action="/players/${player.id}/${id}/${player.card1}/ElegirCartaFaccion1">
												<div class="col-md-6">
													<button type="submit"><img class="img-responsive" src="/resources/images/${card1}.jpg" style="height: 200px; width: 150px;"/></button>
												</div>
											
										</form:form>
										<form:form modelAttribute="match" class="form-horizontal"
											id="add-match-form"
											action="/players/${player.id}/${id}/${player.card2}/ElegirCartaFaccion2">
											
												<div class="col-md-6">
													<button type="submit"><img class="img-responsive" src="/resources/images/${card2}.jpg" style="height: 200px; width: 150px;"/></button>
												</div>
											
										</form:form>
									</div>
								</c:if>
							
						
					</c:if>
					</c:if>
					<c:if test="${mostrarCartas eq false}">
						<div class="col-md-4">
							<h2>Tu faccion es:</h2>
							<div class="col-md-6">
								<img class="img-responsive" src="/resources/images/${card1}.jpg" style="height: 200px; width: 150px;"/>
							</div>
						</div>
					</c:if>
					<c:if test="${voteCondition}">
						<div class="col-md-3">
							<h2>Tu voto: </h2>
							<img class="img-responsive" src="/resources/images/${voteCard}.jpg" style="height: 200px; width: 150px;"/>
						</div>
					</c:if>
					
			</div>
			</c:forEach>
				
				
			</c:if>
	</IdusMartii:layout>
</c:if>