package com.example.ejemplo3
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
data class RegistroClase(
    val nombre: String,
    val matricula: String,
    val asignatura: String,
    val hora: String,
    val fecha: String
)
object Validadores {
    private val regexNombre = Regex("^[A-Za-zÁÉÍÓÚÑÁÉÍÓÚñáéíóúü\\s]{3,60}$")
  private val regexMatricula = Regex("^[0-9]{6,10}$")
    private val regexAsignatura = Regex("^[A-Za-z0-9ÁÉÍÓÚÑñáéíóúü\\s]{3,60}$")
    private val regexHora = Regex("^([01][0-9]|2[0-3]):[0-5][0-9]$")
    private val regexFecha = Regex("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/[0-9]{4}$")
    fun validarNombre(v: String): String? =
        if (v.isBlank()) "El nombre es obligatorio"
        else if (!regexNombre.matches(v)) "Solo se permiten letras y espacios (3-60 caracteres)"
        else null

    fun validarMatricula(v: String): String? =
        if (v.isBlank()) "La matrícula es obligatoria"
        else if (!regexMatricula.matches(v)) "Debe contener solo números (6 a 10 dígitos)"
        else null

    fun validarAsignatura(v: String): String? =
        if (v.isBlank()) "La asignatura es obligatoria"
        else if (!regexAsignatura.matches(v)) "Solo letras, números y espacios (3-60 caracteres)"
        else null

    fun validarHora(v: String): String? =
        if (v.isBlank()) "La hora es obligatoria"
        else if (!regexHora.matches(v)) "Formato inválido, usa HH:mm (ej. 14:30)"
        else null

    fun validarFecha(v: String): String? =
        if (v.isBlank()) "La fecha de entrega es obligatoria"
        else if (!regexFecha.matches(v)) "Formato inválido, usa dd/mm/aaaa (ej. 20/09/2026)"
        else null
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            FormularioRegistro()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioRegistro() {
    // --- Estado de cada campo ---
    var nombre by remember { mutableStateOf("") }
    var matricula by remember { mutableStateOf("") }
    var asignatura by remember { mutableStateOf("") }
    var hora by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }

    // --- Estado de errores (null = sin error) ---
    var errorNombre by remember { mutableStateOf<String?>(null) }
    var errorMatricula by remember { mutableStateOf<String?>(null) }
    var errorAsignatura by remember { mutableStateOf<String?>(null) }
    var errorHora by remember { mutableStateOf<String?>(null) }
    var errorFecha by remember { mutableStateOf<String?>(null) }

    // Dato ya validado que se muestra en la tarjeta (no se persiste en BD)
    var registroGuardado by remember { mutableStateOf<RegistroClase?>(null) }

    fun validarTodo(): Boolean {
        errorNombre = Validadores.validarNombre(nombre)
        errorMatricula = Validadores.validarMatricula(matricula)
        errorAsignatura = Validadores.validarAsignatura(asignatura)
        errorHora = Validadores.validarHora(hora)
        errorFecha = Validadores.validarFecha(fecha)
        return listOf(errorNombre, errorMatricula, errorAsignatura, errorHora, errorFecha)
            .all { it == null }
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val anchoMax = if (maxWidth > 600.dp) 480.dp else maxWidth

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = anchoMax)
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "Registro de clase",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    singleLine = true,
                    isError = errorNombre != null,
                    supportingText = { errorNombre?.let { Text(it) } },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = matricula,
                    onValueChange = { if (it.length <= 10) matricula = it },
                    label = { Text("Matrícula") },
                    singleLine = true,
                    isError = errorMatricula != null,
                    supportingText = { errorMatricula?.let { Text(it) } },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = asignatura,
                    onValueChange = { asignatura = it },
                    label = { Text("Asignatura") },
                    singleLine = true,
                    isError = errorAsignatura != null,
                    supportingText = { errorAsignatura?.let { Text(it) } },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = hora,
                    onValueChange = { if (it.length <= 5) hora = it },
                    label = { Text("Hora que se imparte (HH:mm)") },
                    placeholder = { Text("14:30") },
                    singleLine = true,
                    isError = errorHora != null,
                    supportingText = { errorHora?.let { Text(it) } },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = fecha,
                    onValueChange = { if (it.length <= 10) fecha = it },
                    label = { Text("Fecha de entrega (dd/mm/aaaa)") },
                    placeholder = { Text("20/09/2026") },
                    singleLine = true,
                    isError = errorFecha != null,
                    supportingText = { errorFecha?.let { Text(it) } },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        if (validarTodo()) {
                            registroGuardado = RegistroClase(
                                nombre = nombre,
                                matricula = matricula,
                                asignatura = asignatura,
                                hora = hora,
                                fecha = fecha
                            )
                        } else {
                            // Si hay errores, no se muestra la tarjeta
                            registroGuardado = null
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar")
                }

                //
                registroGuardado?.let { registro ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "Datos capturados",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text("Nombre: ${registro.nombre}")
                            Text("Matrícula: ${registro.matricula}")
                            Text("Asignatura: ${registro.asignatura}")
                            Text("Hora: ${registro.hora}")
                            Text("Fecha de entrega: ${registro.fecha}")
                        }
                    }
                }
            }
        }
    }
}
