package taller1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
						if (continuar.equalsIgnoreCase("s") == false) {
							break;
						}

					} while (!salirUsuarios);

					break;

				case 2:
					menuAnalisis();

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
		} while (salir == false);
		// scan.close();
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
				System.out.println("Bienvenido " + usuario + "\n" + "Que desea realizar\n" + "1) Registrar actividad\n"
						+ "2) Modificar Actividad\n" + "3) Eliminar actividad\n" + "4) Cambiar contraseña\n"
						+ "5) Salir\n");
				System.out.print("Elección: ");
				Integer selec = Integer.valueOf(scan.nextLine());

				switch (selec) {
				case 1:
					System.out.print("Ingresar actividad: ");
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
					String[] tempList = new String[100];
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

								} catch (Exception e) {
									System.out.println("Ingrese un valor valido;");
								}
							}

						} catch (Exception e) {
							System.out.println("Ingresar un valor válido");
						}
					} while (false);

					break;
				case 3:
					System.out.println("cual actividad deseas modificar?");
					String[] tempListborrar = new String[100];
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
							modificarArchivo("Registros.txt", lineaModificar, "", 3);
							System.out.println("actividad eliminada con exito");
						} catch (Exception e) {
							System.out.println("ingresar un valor válido");
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
	
	private static void menuAnalisis() {
		boolean option = false;
		do {
			try {
				Scanner scanner = new Scanner(System.in);
				
				System.out.println("Bienvenido al menu de analisis!\r\n"
						+ "\r\n"
						+ "Que deseas realizar?\n");
				
				System.out.println("1) Actividad más realizada\r\n"
						+ "2) Actividad más realizada por cada usuario\r\n"
						+ "3) Usuario con mayor procastinacion\r\n"
						+ "4) Ver todas las actividades\r\n"
						+ "5) Salir");
				int eleccionAnalisis = Integer.parseInt(scanner.nextLine());
				
				switch (eleccionAnalisis) {
					case 1:
					
						break;
					case 2:
						break;
					case 3:
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
				
			}catch (NumberFormatException e) {
				System.out.println("por favor utilizar un valor valido\n");
			}
			
		} while(!option);
		
	}

	private static void modificarArchivo(String archivo, String original, String modificación, int Tipo) {
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

				while ((palabras = lector.readLine()) != null) {
					lista += palabras + "\r\n";
				}
				lector.close();

				String textoNuevo = lista.replaceAll(original, modificación);

				FileWriter escritor = new FileWriter(archivo);
				escritor.write(textoNuevo);
				escritor.close();

			} else if (Tipo == 3) {
				BufferedReader lector = new BufferedReader(new FileReader(archivo));

				String palabras = "";
				String lista = "";

				while ((palabras = lector.readLine()) != null) {
					if (!palabras.equalsIgnoreCase(original)) {
						lista += palabras + "\r\n";
					}
				}
				lector.close();

				String textoNuevo = lista.replaceAll(original, modificación);

				FileWriter escritor = new FileWriter(archivo);
				escritor.write(textoNuevo);
				escritor.close();
			}

		} catch (Exception e) {
			System.out.println("Archivo no encontrado");
		}
	}

}
