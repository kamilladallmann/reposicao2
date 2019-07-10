      <!DOCTYPE html>
      <html lang="en">

      <head>
          <meta charset="UTF-8">
          <meta name="viewport" content="width=device-width, initial-scale=1.0">
          <meta http-equiv="X-UA-Compatible" content="ie=edge">
          <title>Gerencia Requerimentos</title>
          <!-- Latest compiled and minified CSS -->
          <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

          <!-- jQuery library -->
          <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>

          <!-- Popper JS -->
          <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>

          <!-- Latest compiled JavaScript -->
          <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
      </head>

      <body>
          <div class="container">
              <div class="jumbotron">
                  <h1>Gerenciamento de Requerimentos</h1>
                  <p>Essa página é responsável por fazer o geranciamento de professores. </p>
              </div>
              <div class="row">
                  <div class="col">
                      <form action="/requerimento/alterar" method="post">
                          <div class="form-group">
                              <label for="dataAusencia">Data da Ausência:</label>
                              <input value="${(requerimentoAtual.dataAusencia)!}" name="dataAusencia" type="text" class="form-control" id="dataAusencia">
                          </div>
                          <div class="form-group">
                              <label for="dataReposicao">Data da Reposição:</label>
                              <input value="${(requerimentoAtual.dataReposicao)!}"  name="dataReposicao" type="text" class="form-control" id="dataReposicao">
                          </div>
                          <div class="form-group">
                              <label for="professor">Professor:</label>
                              <input value="${(requerimentoAtual.professor)!}"  name="professor" type="text" class="form-control" id="professor">
                          </div>
                          <div class="form-group">
                               <label for="coordenador">Coordenador:</label>
                               <input value="${(requerimentoAtual.coordenador)!}"  name="coordenador" type="text" class="form-control" id="coordenador">
                          </div>
                          <div class="form-group">
                               <label for="disciplina">Disciplina:</label>
                               <input value="${(requerimentoAtual.disciplina)!}"  name="disciplina" type="text" class="form-control" id="disciplina">
                          </div>
                          <div class="form-group">
                               <label for="aulasParaRepor">Aulas que serão repostas:</label>
                               <input value="${(requerimentoAtual.aulasParaRepor)!}"  name="aulasParaRepor" type="text" class="form-control" id="aulasParaRepor">
                          </div>
                          <div class="form-group">
                               <label for="qtddAulasTotal"> Quantidade de Aulas a serem repostas no total:</label>
                               <input value="${(requerimentoAtual.qtddAulasTotal)!}"  name="qtddAulasTotal" type="text" class="form-control" id="qtddAulasTotal">
                          </div>
                          <div class="form-group">
                               <label for="alunosConcordaram">Lista de Aunos que concordaram:</label>
                               <input value="${(requerimentoAtual.alunosConcordaram)!}"  name="alunosConcordaram" type="text" class="form-control" id="alunosConcordaram">
                          </div>
                          <div class="form-group">
                               <label for="motivoAusencia">Motivo da Ausência:</label>
                               <input value="${(requerimentoAtual.motivoAusencia)!}"  name="motivoAusencia" type="text" class="form-control" id="motivoAusencia">
                          </div>
                          <div class="form-group">
                               <label for="porcentagemConcordam">% de alunos que concordaram:</label>
                               <input value="${(requerimentoAtual.porcentagemConcordam)!}"  name="porcentagemConcordam" type="text" class="form-control" id="porcentagemConcordam">
                          </div>
                          <div class="form-group">
                               <label for="campus">Campus:</label>
                               <input value="${(requerimentoAtual.campus)!}"  name="campus" type="text" class="form-control" id="campus">
                          </div>
                          <div class="form-group">
                               <label for="departamento">Departamento:</label>
                               <input value="${(requerimentoAtual.departamento)!}"  name="departamento" type="text" class="form-control" id="departamento">
                          </div>
                          <div class="form-group">
                               <label for="file">Lista de Anuência:</label>
                               <input name="file" type="file" class="form-control" id="file">
                          </div>
                          <button type="submit" class="btn btn-primary">Criar</button>
                      </form>

                  </div>
              </div>
              <div class="row">
                  <div class="col">
                      <table class="table table-striped table-hover">
                          <thead class="thead-dark">
                              <tr>
                                  <th>Data Ausencia</th>
                                  <th>Data Reposicao</th>
                                  <th>Professor</th>
                                  <th>Coordenador</th>
                                  <th>Disciplina</th>
                                  <th>Aulas</th>
                                  <th>Quantidade</th>
                                  <th>Alunos</th>
                                  <th>Motivo</th>
                                  <th>%</th>
                                  <th>Campus</th>
                                  <th>Departamento</th>
                                  <th>Ações</th>
                              </tr>
                          </thead>
                          <tbody>
                              <#list requerimentos as requerimento>
                                  <tr>
                                      <td>${requerimento.dataAusencia}</td>
                                      <td>${requerimento.dataReposicao}</td>
                                      <td>${requerimento.professor}</td>
                                      <th>${requerimento.coordenador}</td>
                                      <th>${requerimento.disciplina}</td>
                                      <th>${requerimento.aulasParaRepor}</td>
                                      <th>${requerimento.qtddAulasTotal}</td>
                                      <th>${requerimento.alunosConcordaram}</td>
                                      <th>${requerimento.motivoAusencia}</td>
                                      <th>${requerimento.porcentagemConcordam}</td>
                                      <td>${requerimento.campus}</td>
                                      <td>${requerimento.departamento}</td>
                                      <td>
                                          <a href="/requerimento/prepara-alterar?id=${requerimento.id}">Alterar</a>
                                          <a href="/requerimento/excluir?id=${requerimento.id}">Excluir</a>
                                      </td>
                                  </tr>
                              </#list>
                          </tbody>
                      </table>
                  </div>
              </div>
          </div>
      </body>

      </html>