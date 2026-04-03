package taller1;

/* Ignacio Araya Munizaga 21.824.045-3 Ingenieria en tecnologias de la información
 * 
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {

		Scanner scan = new Scanner(System.in);
		Scanner scanArch = null;
		boolean salir = false;

		String[][] usuarios = new String[3][2];
		usuarios = cargarUsuarios(usuarios, scanArch);
		do {
			try {
				System.out.println("1) Menú de Usuarios \n" + "2) Menú de Análisis \n" + "3) Salir \n");
				System.out.print("Elección: ");

				Integer Elección = Integer.valueOf(scan.nextLine());
				switch (Elección) {
				case 1:
					boolean validarUsuario = false;
					boolean salirUsuarios = false;
					do {
						System.out.print("Usuario: ");
						String usuario = scan.nextLine();
						System.out.print("Contraseña: ");
						String contraseña = scan.nextLine();
						System.out.println();
						for (int i = 0; i < usuarios.length; i++) {
							if (usuario.equals(usuarios[i][0]) && contraseña.equals(usuarios[i][1])) {
								validarUsuario = true;
							}
						}
						if (validarUsuario) {
							salirUsuarios = menuUsuarios(scan, usuarios, usuario);
						} else {
							System.out.println("No se pudo validar el usuario o la contraseña");
						}
						System.out.print(
								"Desea continuar con otro usuario? S/N (de ingresar un valor no válido se le devolvera automaticamente): ");
						String continuar = scan.nextLine();
						if (!continuar.equalsIgnoreCase("s")) {
							break;
						}

					} while (!salirUsuarios);

					break;

				case 2:
					menuAnalisis(scan);

					break;
				case 3:
					salir = true;
					break;
				default:
					System.out.println("Seleccionar una de las opciones\n");
					break;
				}

			} catch (NumberFormatException e) {
				System.out.println("Por favor utilizar un valor válido\n");
			}
		} while (!salir);
		scan.close();
	}

	private static String[][] cargarUsuarios(String[][] usuarios, Scanner scan) throws FileNotFoundException {
		scan = new Scanner(new File("Usuarios.txt"));
		int incremento = 0;
		while (scan.hasNextLine()) {
			String linea = scan.nextLine();
			String[] partes = linea.split(";");
			usuarios[incremento][0] = partes[0];
			usuarios[incremento][1] = partes[1];
			incremento++;
		}
		scan.close();
		return usuarios;
	}

	private static boolean menuUsuarios(Scanner scan, String[][] usuarios, String usuario) {
		do {
			try {
				System.out.println("Bienvenido " + usuario + "\n" + "Que desea realizar?\n" + "1) Registrar actividad\n"
						+ "2) Modificar Actividad\n" + "3) Eliminar actividad\n" + "4) Cambiar contraseña\n"
						+ "5) Salir\n");
				System.out.print("Elección: ");
				Integer selec = Integer.valueOf(scan.nextLine());

				switch (selec) {
				case 1:
					System.out.print("Ingresar actividad: \n");
					String actividad = scan.nextLine();
					try {
						System.out.print("Ingresar horas: ");
						Integer horas = Integer.valueOf(scan.nextLine());
						System.out.print("Ingresar día: ");
						Integer dia = Integer.valueOf(scan.nextLine());
						System.out.print("Ingresar mes: ");
						Integer mes = Integer.valueOf(scan.nextLine());
						System.out.print("Ingresar año: ");
						Integer año = Integer.valueOf(scan.nextLine());
						if (dia < 31 && dia > 0 && mes < 12 && mes > 0 && año > 0) {
							String registrar = usuario + ";" + dia + "/" + mes + "/" + año + ";" + horas + ";"
									+ actividad;
							modificarArchivo("Registros.txt", registrar, "", 0);
							System.out.println("Actividad registrada");
						} else {
							System.out.println("Ingresar una fecha adecuada");
						}
					} catch (Exception e) {
						System.out.println("Ingrese un valor válido");
					}

					break;
				case 2:
					System.out.println("cual actividad deseas modificar?");
					String[] tempList = new String[300];
					do {
						try {
							Scanner scanArch = new Scanner(new File("Registros.txt"));
							int contador = 0;
							System.out.println("Seleccione una actividad \n" + "0) Regresar");
							while (scanArch.hasNextLine()) {
								String linea = scanArch.nextLine();
								String[] partes = linea.split(";");
								if (usuario.equalsIgnoreCase(partes[0]) && linea != null) {
									tempList[contador++] = linea;
									System.out.println(contador + ") " + linea);
								}
							}
							scanArch.close();
							System.out.print("Elección: ");
							Integer indexLinea = Integer.valueOf(scan.nextLine());
							if (indexLinea == 0) {
								System.out.println("");
							} else {
								try {
									String lineaModificar = tempList[indexLinea - 1];
									System.out.print("Que desea modifiacar?\n" + "1) Fecha\n" + "2) Duración\n"
											+ "3) Tipo de actividad\n" + "Elección: \n");
									Integer eleccion = Integer.valueOf(scan.nextLine());
									String lineaModificada = "";
									String[] partir;
									switch (eleccion) {
									case 1:
										System.out.print("Ingresar día: ");
										Integer dia = Integer.valueOf(scan.nextLine());
										System.out.print("Ingresar mes: ");
										Integer mes = Integer.valueOf(scan.nextLine());
										System.out.print("Ingresar año: ");
										Integer año = Integer.valueOf(scan.nextLine());
										if (dia < 31 && dia > 0 && mes < 12 && mes > 0 && año > 0) {
											String fecha = dia + "/" + mes + "/" + año;
											partir = lineaModificar.split(";");
											lineaModificada = partir[0] + ";" + fecha + ";" + partir[2] + ";"
													+ partir[3];

										} else {
											System.out.println("Ingresar una fecha adecuada");
										}
										break;
									case 2:

										System.out.println("Ingresar duración: ");
										Integer duracion = Integer.valueOf(scan.nextLine());
										partir = lineaModificar.split(";");
										lineaModificada = partir[0] + ";" + partir[1] + ";" + duracion + ";"
												+ partir[3];
										break;
									case 3:
										System.out.print("Ingresar actividad: ");
										String act = scan.nextLine();
										partir = lineaModificar.split(";");
										lineaModificada = partir[0] + ";" + partir[1] + ";" + partir[2] + ";" + act;

										break;
									default:
										break;
									}
									modificarArchivo("Registros.txt", lineaModificar, lineaModificada, 1);

								} catch (NumberFormatException e) {
									System.out.println("Ingrese un valor valido;");
								} catch (IndexOutOfBoundsException e) {
									System.out.println("El archivo supera el límite de 300 registros");
								}
							}

						} catch (Exception e) {
							System.out.println("Ingresar un valor válido");
						}
					} while (false);

					break;
				case 3:
					System.out.println("cual actividad deseas eliminar?");
					String[] tempListborrar = new String[300];
					do {
						try {
							Scanner scanArch = new Scanner(new File("Registros.txt"));
							int contador = 0;
							System.out.println("Seleccione una actividad \n" + "0) Regresar");
							while (scanArch.hasNextLine()) {
								String linea = scanArch.nextLine();
								String[] partes = linea.split(";");
								if (usuario.equalsIgnoreCase(partes[0]) && linea != null) {
									tempListborrar[contador++] = linea;
									System.out.println(contador + ") " + linea);
								}
							}
							scanArch.close();
							System.out.print("Elección: ");
							Integer indexLinea = Integer.valueOf(scan.nextLine());
							if (indexLinea == 0) {
								System.out.println("");
							}
							String lineaModificar = tempListborrar[indexLinea - 1];
							modificarArchivo("Registros.txt", lineaModificar, "", 2);
							System.out.println("actividad eliminada con exito.");
						} catch (NumberFormatException e) {
							System.out.println("Ingresar un valor válido.");
						} catch (IndexOutOfBoundsException e) {
							System.out.println("El archivo supera el límite de 300 registros.");
						}
					} while (false);
					break;
				case 4:
					System.out.println("Ingresar nueva contraseña: ");
					String contraseñaNueva = scan.nextLine();
					String usuarioOriginal = "";
					String usuarioNuevo = usuario + ";" + contraseñaNueva;
					for (int i = 0; i < usuarios.length; i++) {
						if (usuarios[i][0] == usuario) {
							usuarioOriginal = usuario + ";" + usuarios[i][1];
						}
					}
					modificarArchivo("Usuarios.txt", usuarioOriginal, usuarioNuevo, 2);
					break;
				case 5:

					break;
				default:
					break;
				}

			} catch (Exception e) {
				System.out.println("Ingresar un valor válido");
			} finally {
				System.out.print(
						"Desea continuar en el menú de usuario? S/N (de ingresar un valor no válido se le devolvera automaticamente): ");
				String continuar = scan.nextLine();
				if (continuar.equalsIgnoreCase("s") == false) {
					break;
				}
			}

		} while (true);

		return true;
	}

	private static void menuAnalisis(Scanner scanner) {
		boolean option = false;
		do {
			try {

				System.out.println("Bienvenido al menu de analisis!\r\n" + "\r\n" + "Que deseas realizar?\n");

				System.out.println("1) Actividad más realizada\r\n" + "2) Actividad más realizada por cada usuario\r\n"
						+ "3) Usuario con mayor procastinacion\r\n" + "4) Ver todas las actividades\r\n" + "5) Salir");
				int eleccionAnalisis = Integer.parseInt(scanner.nextLine());

				switch (eleccionAnalisis) {
				case 1:
					actividadMasRealizadaAnalisis();
					break;
				case 2:
					actividadMasRealizadaPorUsuario();
					break;
				case 3:
					usuarioMayorProcrastinacion();
					break;
				case 4:
					break;
				case 5:
					option = true;
					break;

				default:
					System.out.println("Seleccione una de las opciones existentes.\n");
					break;
				}

			} catch (NumberFormatException e) {
				System.out.println("por favor utilizar un valor valido\n");
			}
		} while (!option);
	}

	private static void modificarArchivo(String archivo, String original, String modificación, int Tipo) {
		Integer contadorLineas = 0;
		try {
			Scanner scanCont = new Scanner(new File(archivo));
			while (scanCont.hasNextLine()) {
				contadorLineas++;
				scanCont.nextLine();
			}
			scanCont.close();

		} catch (Exception e) {
		}
		try {
			if (Tipo == 0) {
				BufferedWriter escritor = new BufferedWriter(new FileWriter(archivo, true));
				escritor.newLine();
				escritor.write(original);
				escritor.close();
			} else if (Tipo == 1) {

				BufferedReader lector = new BufferedReader(new FileReader(archivo));
				String palabras = "";
				String lista = "";
				Integer contador = 0;
				while ((palabras = lector.readLine()) != null) {
					if (contador == contadorLineas - 1) {
						lista += palabras;
					} else {
						lista += palabras + "\r\n";
						contador++;
					}
				}
				lector.close();

				String textoNuevo = lista.replaceAll(original, modificación);
				FileWriter escritor = new FileWriter(archivo);
				escritor.write(textoNuevo);
				escritor.close();
			} else if (Tipo == 2) {
				BufferedReader lector = new BufferedReader(new FileReader(archivo));

				String palabras = "";
				String lista = "";
				Integer contador = 0;

				while ((palabras = lector.readLine()) != null) {
					if (contador == contadorLineas - 1) {
						lista += palabras;
					} else {
						if (!palabras.equalsIgnoreCase(original)) {
							lista += palabras + "\r\n";
						}
						contador++;
					}
				}
				lector.close();

				String textoNuevo = lista.replaceAll(original, modificación);

				FileWriter escritor = new FileWriter(archivo);
				escritor.write(textoNuevo);
				escritor.close();
			}

		} catch (IOException e) {
			System.out.println("Archivo no encontrado");
		}
	}

	/*
	 * actividad mas reciente opcion 1 y 2 de Menu Analisis
	 */
	private static void actividadMasRealizadaAnalisis() {
		try {
			Scanner scanner = new Scanner(new File("Registros.txt"));
			// teniendo en cuenta que maximo de actividades son 300.... vectores de 300
			// supongo
			String[] actividadeStrings = new String[300];
			int[] totalHoras = new int[300];
			int contadorActividades = 0;

			// el lector para registros....
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] parteStrings = line.split(";");

				String actiString = parteStrings[3];
				int horas = Integer.parseInt(parteStrings[2]);

				// if elemento in lista
				boolean ifElemento = false;

				for (int i = 0; i < contadorActividades; i++) {
					if (actividadeStrings[i].equalsIgnoreCase(actiString)) {
						totalHoras[i] += horas;
						ifElemento = true;
						break;
					}
				}
				if (!ifElemento) {
					actividadeStrings[contadorActividades] = actiString;
					totalHoras[contadorActividades] = horas;
					contadorActividades++;
				}
			}
			scanner.close();
			// scanner cerrado

			/*
			 * ahora si para ver actividades con mayor horas y presencia
			 */
			if (contadorActividades > 0) {

				int maxHoras = totalHoras[0];
				String actividadHoraMaxima = actividadeStrings[0];

				for (int i = 1; i < contadorActividades; i++) {

					if (totalHoras[i] > maxHoras) {

						maxHoras = totalHoras[i];
						actividadHoraMaxima = actividadeStrings[i];
					}
				}

				System.out.println("\n --- Actividad mas realizada ---");
				System.out.println("Actividad: " + actividadHoraMaxima);
				System.out.println("Total horas: " + maxHoras + " horas\n");

			} else {
				// no creo que se trigueree esta cosa pero mejor prevenir
				System.out.println("no hay actividades registradas \n");
			}

		} catch (FileNotFoundException e) {
			System.out.println("ERROR de lectura " + e);
		}

	}

	private static void actividadMasRealizadaPorUsuario() {
		try {
			Scanner archivoScanner = new Scanner(new File("Registros.txt"));

			// se supone que el limite eran 300
			String[] usuariosTemporal = new String[300];
			String[] actividadesTemporal = new String[300];
			int[] horasTemporales = new int[300];

			// esto es para sumar con suerte los registros
			int contadorRegistros = 0;

			while (archivoScanner.hasNextLine()) {

				String linea = archivoScanner.nextLine();
				String[] partes = linea.split(";");

				if (partes.length == 4) {
					String usuarioString = partes[0];
					String actividadString = partes[3];
					int horas = Integer.parseInt(partes[2]);

					// esto es para ver si hay usuarios con actividad
					boolean encontrada = false;

					for (int i = 0; i < contadorRegistros; i++) {
						if (usuariosTemporal[i].equalsIgnoreCase(usuarioString) && actividadesTemporal[i].equalsIgnoreCase(actividadString)) {
							horasTemporales[i] += horas;
							encontrada = true;
							break;
						}
					}

					if (!encontrada) {
						usuariosTemporal[contadorRegistros] = usuarioString;
						actividadesTemporal[contadorRegistros] = actividadString;
						horasTemporales[contadorRegistros] = horas;
						contadorRegistros++;
					}
				}
			}
			archivoScanner.close();

			String[] usuariosUnicosStrings = new String[300];
			String[] actividadesMax = new String[300];
			int[] horasMax = new int[300];
			int contadorUsuarios = 0;
			// esto se supone que es para ver si ya encontre al usuario
			for (int i = 0; i < contadorRegistros; i++) {

				String usuarioActual = usuariosTemporal[i];
				boolean usuarioExistente = false;

				for (int j = 0; j < contadorUsuarios; j++) {
					if (usuariosUnicosStrings[j].equalsIgnoreCase(usuarioActual)) {
						if (horasTemporales[i] > horasMax[j]) {
							horasMax[j] = horasTemporales[i];
							actividadesMax[j] = actividadesTemporal[i];
						}

						usuarioExistente = true;
						break;
					}
				}

				if (!usuarioExistente) {
					usuariosUnicosStrings[contadorUsuarios] = usuarioActual;
					actividadesMax[contadorUsuarios] = actividadesTemporal[i];
					horasMax[contadorUsuarios] = horasTemporales[i];
					contadorUsuarios++;
				}
			}
			System.out.println("\n Actividades mas realizadas por cada usuario: \n");

			if (contadorUsuarios > 0) {

				for (int i = 0; i < contadorUsuarios; i++) {

					System.out.println("* " + usuariosUnicosStrings[i] + " -> " + actividadesMax[i] + " con "
							+ horasMax[i] + " horas registradas\n");
				}
			} else {
				System.out.println("No hay actividades registradas.\n");
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error: No se encontró el archivo Registros.txt\n");

		}
	}
	//al menos no me ha tirado error pero hay que echarle vistazo
	private static void usuarioMayorProcrastinacion() {
		try {
			Scanner scanner = new Scanner(new File("Registros.txt"));
			//los vectores
			String[] usuariosTempStrings = new String[300];
			int[] totalHorasTemporal = new int[300];
			int contadorUsuarios = 0;

			while (scanner.hasNextLine()) {
				String lineaString = scanner.nextLine();
				String[] partesStrings = lineaString.split(";");

				if (partesStrings.length == 4) {
					String usuario = partesStrings[0];
					int horas = Integer.parseInt(partesStrings[2]);

					boolean encontrado = false;
					for (int i = 0; i < contadorUsuarios; i++) {
						if (usuariosTempStrings[i].equalsIgnoreCase(usuario)) {
							totalHorasTemporal[i] += horas;
							encontrado = true;
							break;
						}
					}

					if (!encontrado) {
						usuariosTempStrings[contadorUsuarios] = usuario;
						totalHorasTemporal[contadorUsuarios] = horas;
						contadorUsuarios++;
					}
				}
			}
			scanner.close();
			
			if (contadorUsuarios > 0) {
				int maxHoras = totalHorasTemporal[0];
				String usuarioMax = usuariosTempStrings[0];

				for (int i = 1; i < contadorUsuarios; i++) {
					if (totalHorasTemporal[i] > maxHoras) {
						maxHoras = totalHorasTemporal[i];
						usuarioMax = usuariosTempStrings[i];
					}
				}

				System.out.println("\n Usuario con mayor procastinacion: \n");
				System.out.println("Usuario: " + usuarioMax);
				System.out.println("Total horas: " + maxHoras + " horas \n");
			} else {
				System.out.println("No hay registros de actividades. \n");
			}

		} catch (FileNotFoundException e) {
			System.out.println("Error: " + e);
		}
	}

}