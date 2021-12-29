package ec.org.pms.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.org.pms.models.Eje;
import ec.org.pms.models.Indicador;
import ec.org.pms.models.UserRole;
import ec.org.pms.models.ValorIndicador;
import ec.org.pms.payload.request.IndicadorRequest;
import ec.org.pms.payload.response.SemaforizacionResponse;
import ec.org.pms.payload.response.ejeResponse.DatosBarra;
import ec.org.pms.payload.response.ejeResponse.DatosEje;
import ec.org.pms.payload.response.indicadorResponse.Datos;
import ec.org.pms.payload.response.indicadorResponse.Hijo;
import ec.org.pms.payload.response.indicadorResponse.Padre;
import ec.org.pms.payload.response.indicadorResponse.Root;
import ec.org.pms.repositories.EjeRepository;
import ec.org.pms.repositories.IndicadorRepository;
import ec.org.pms.repositories.UserRoleRepository;
import ec.org.pms.repositories.ValorIndicadorRepository;
import ec.org.pms.services.IndicadorService;

@Service("indicadorService")
public class IndicadorServiceImpl implements IndicadorService {

	@Autowired
	private EjeRepository ejeRepository;
	@Autowired
	private IndicadorRepository indicadorRepository;
	@Autowired
	private ValorIndicadorRepository valorIndicadorRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	public List<Root> findIndicadores(Integer personaId) {
		List<Eje> ejes = ejeRepository.findByIdLessThan(10);
		List<Eje> determinantes = ejeRepository.findByIdGreaterThan(10);
		List<Indicador> indicadores = (List<Indicador>) indicadorRepository.findAll();
		UserRole user = userRoleRepository.findById(personaId).get();
		Integer cantonId = user.getEntId();
		List<ValorIndicador> resultados = valorIndicadorRepository.findByCantonId(cantonId);
		Datos data;
		Hijo childre;
		List<Hijo> childres;
		List<Hijo> childress;
		Padre children;
		List<Padre> childrens;
		List<Padre> childrenss;
		Root root;
		List<Root> roots;

		childres = new ArrayList<>();
		for (Indicador indicador : indicadores) {
			data = new Datos();
			data.setIndicadorId(indicador.getId());
			data.setEjeId(indicador.getEjeId());
			data.setCode(indicador.getCode());
			data.setName(indicador.getName());
			data.setDescription(indicador.getDescription());
			data.setType(indicador.getType());
			for (ValorIndicador valorIndicador : resultados) {
				if (indicador.getId().equals(valorIndicador.getEjeId())) {
					if (valorIndicador != null && valorIndicador.getEjeId() > 0) {
						data.setValue(valorIndicador.getValor());
					} else {
						data.setValue(null);
					}
				}
			}
			data.setOption1(indicador.getInicial());
			data.setOption2(indicador.getSatisfactorio());
			data.setOption3(indicador.getOptimo());
			childre = new Hijo();
			childre.setKey(String.valueOf(indicador.getId()));
			childre.setData(data);
			childres.add(childre);
		}

		childrens = new ArrayList<>();
		for (Eje determinante : determinantes) {
			children = new Padre();
			children.setKey(String.valueOf(determinante.getId()));
			data = new Datos();
			data.setName(determinante.getName());
			data.setEjeId(determinante.getParent());
			children.setData(data);
			childress = new ArrayList<>();
			for (Hijo hijo : childres) {
				if (determinante.getId().equals(hijo.getData().getEjeId())) {
					childress.add(hijo);
				}
			}
			children.setChildren(childress);
			childrens.add(children);
		}

		roots = new ArrayList<>();
		for (Eje eje : ejes) {
			root = new Root();
			root.setKey(String.valueOf(eje.getId() - 1));
			data = new Datos();
			data.setName(eje.getName());
			data.setEjeId(eje.getId());
			root.setData(data);
			childrenss = new ArrayList<>();
			for (Padre childen : childrens) {
				if (eje.getId().equals(childen.getData().getEjeId())) {
					childrenss.add(childen);
				}
			}
			root.setChildren(childrenss);
			roots.add(root);
		}
		int i = 0;
		for (Root roo : roots) {
			List<Padre> padres = roo.getChildren();
			int j = 0;
			for (Padre padre : padres) {
				padre.setKey(roo.getKey() + "-" + String.valueOf(j));
				List<Hijo> hijos = padre.getChildren();
				int k = 0;
				for (Hijo hijo : hijos) {
					hijo.setKey(roo.getKey() + "-" + String.valueOf(j) + "-" + String.valueOf(k));
					k = k + 1;
				}
				j = j + 1;
			}
			i = i + 1;
		}
		return roots;
	}

	@Override
	public List<Root> saveIndicador(IndicadorRequest indicadorSave) {
		ValorIndicador indicador = new ValorIndicador();
		int level = indicadorRepository.findBycode(Integer.valueOf(indicadorSave.getCode())).getId();
		UserRole userRol = userRoleRepository.findFirstByPersonId(indicadorSave.getUserId());

		indicador.setId(indicadorSave.getIndicadorId());
		indicador.setCantonId(userRol.getEntId());
		indicador.setEjeId(level);
		indicador.setValor(Double.valueOf(indicadorSave.getValue()));
		indicador.setPersonaId(userRol.getId());
		indicador.setEstado(91);

		indicador = valorIndicadorRepository.save(indicador);

		return findIndicadores(indicadorSave.getUserId());
	}

	@Override
	public List<SemaforizacionResponse> semaforizacion(Integer personaId) {
		List<Eje> ejes = ejeRepository.findByIdLessThan(10);
		List<Eje> determinantes = ejeRepository.findByIdGreaterThan(10);
		List<Indicador> indicadores = (List<Indicador>) indicadorRepository.findAll();
		SemaforizacionResponse semaforo;
		List<SemaforizacionResponse> semaforos = new ArrayList<>();

		for (Eje eje : ejes) {
			semaforo = new SemaforizacionResponse();
			semaforo.setEje(eje.getName());
			semaforos.add(semaforo);
			for (Eje determinante : determinantes) {
				if (determinante.getParent().equals(eje.getId())) {
					semaforo = new SemaforizacionResponse();
					semaforo.setDeterminante(determinante.getName());
					semaforos.add(semaforo);
					for (Indicador indicador : indicadores) {
						if (indicador.getEjeId().equals(determinante.getId())) {
							semaforo = new SemaforizacionResponse();
							semaforo.setIndicador(indicador.getName());
							UserRole user = userRoleRepository.findById(personaId).get();
							Integer cantonId = user.getEntId();
							ValorIndicador resultado = valorIndicadorRepository.findByCantonIdAndEjeId(cantonId,
									indicador.getId());

							if (resultado != null) {
								if (indicador.getInicial() != null) {
									double DoubleValue = resultado.getValor();
									int IntValue = (int) DoubleValue;
									switch (IntValue) {
									case 1:
										semaforo.setSemaforizacion("red");
										break;
									case 2:
										semaforo.setSemaforizacion("yellow");
										break;
									case 3:
										semaforo.setSemaforizacion("green");
										break;

									default:
										break;
									}
								}
							}

							semaforos.add(semaforo);
						}
					}
				}
			}
		}
		return semaforos;
	}

	@Override
	public DatosBarra ejes(Integer personaId) {
		List<Eje> ejes = ejeRepository.findByIdLessThan(10);
		List<Eje> determinantes = ejeRepository.findByIdGreaterThan(10);
		List<Indicador> indicadores = (List<Indicador>) indicadorRepository.findAll();
		DatosBarra barData = new DatosBarra();
		DatosEje datosEje;
		List<DatosEje> datosEjes = new ArrayList<>();
		String[] ejesArreglo = new String[7];
		Integer[] verdesArreglo = new Integer[7];
		Integer[] amarrillosArreglo = new Integer[7];
		Integer[] rojosArreglo = new Integer[7];
		int verdes = 0;
		int amarillos = 0;
		int rojos = 0;
		
		UserRole user = userRoleRepository.findById(personaId).get();
		Integer cantonId = user.getEntId();

		int i = 0;
		for (Eje eje : ejes) {
			ejesArreglo[i] = eje.getName();
			verdes = 0;
			amarillos = 0;
			rojos = 0;
			for (Eje determinante : determinantes) {
				if (determinante.getParent().equals(eje.getId())) {
					
					for (Indicador indicador : indicadores) {
						if (indicador.getEjeId().equals(determinante.getId())) {
							ValorIndicador resultado = valorIndicadorRepository.findByCantonIdAndEjeId(cantonId,
									indicador.getId());
							
							if (resultado != null) {
								if (indicador.getInicial() != null) {
									double DoubleValue = resultado.getValor();
									int IntValue = (int) DoubleValue;
									System.out.println(IntValue);
									switch (IntValue) {
									case 1:
										rojos = rojos + 1;
										break;
									case 2:
										amarillos = amarillos + 1;
										break;
									case 3:
										verdes = verdes + 1;
										break;
									default:
										break;
									}
								}
							}
						}
					}

					;
				}
			}
			verdesArreglo[i] = verdes;
			amarrillosArreglo[i] = amarillos;
			rojosArreglo[i] = rojos;
			i = i + 1;
		}
		datosEje = new DatosEje();
		datosEje.setLabel("Optimos");
		datosEje.setBackgroundColor("green");
		datosEje.setData(verdesArreglo);
		
		datosEjes.add(datosEje);
		System.out.println(datosEjes);
		
		datosEje = new DatosEje();
		datosEje.setLabel("Satisfactorios");
		datosEje.setBackgroundColor("yellow");
		datosEje.setData(amarrillosArreglo);
		
		datosEjes.add(datosEje);
		System.out.println(datosEjes);
		
		datosEje = new DatosEje();
		datosEje.setLabel("Iniciales");
		datosEje.setBackgroundColor("red");
		datosEje.setData(rojosArreglo);
		
		datosEjes.add(datosEje);
		
		System.out.println(datosEjes);
		
		barData.setDatasets(datosEjes);
		barData.setLabels(ejesArreglo);

		return barData;
	}

}
