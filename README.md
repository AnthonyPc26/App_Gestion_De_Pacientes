# 👩‍⚕️ MediTrack – Gestión de Pacientes en Zonas de Emergencia 🚑

![Version](https://img.shields.io/badge/MediTrack-v1.0-blueviolet?style=for-the-badge)
![Estado](https://img.shields.io/badge/Estado-En%20Desarrollo-orange?style=for-the-badge)
![Plataforma](https://img.shields.io/badge/Plataforma-Android-green?style=for-the-badge)

<table>
  <tr>
    <td>
      🩺 <em>Aplicación médica móvil para profesionales que trabajan bajo presión.</em>
    </td>
    <td>
<img src="https://raw.githubusercontent.com/AnthonyPc26/App_Gestion_De_Pacientes-GRUPO5/d7c1c679cb3fcd71708c4f3beec50243434369e8/ImagenesReadme/hello-android.gif" width="200">
    </td>
  </tr>
</table>

---
## 🧑‍🎓 Integrantes del Proyecto

<table>
  <tr>
    <td style="vertical-align: top; padding-right: 30px;">
      <ul style="list-style: none; padding: 0; font-size: 16px; line-height: 1.8;">
        <li> 👨‍💻 <strong>Pachas Contreras, Anthony</strong></li>
        <li> 👨‍💻 <strong>Ramos Chavez, Jesus</strong></li>
        <li> 👨‍💻 <strong>Diaz Castaneda, Jose Manuel</strong></li>
        <li> 👨‍💻 <strong>Ramirez Chacchi, Jordy</strong></li>
        <li> 👨‍💻 <strong>Leon Chuquimajo, Roberth</strong></li>
      </ul>
    </td>
    <td>
      <img src="https://raw.githubusercontent.com/AnthonyPc26/App_Gestion_De_Pacientes-GRUPO5/main/ImagenesReadme/grupo.gif" width="450">
    </td>
  </tr>
</table>



---

## 🩺 Alcance del Proyecto – MediTrack

**MediTrack** es una aplicación móvil orientada a la **gestión de pacientes en zonas de emergencia y atención médica rápida**. Su enfoque principal es brindar a los profesionales de salud una herramienta **ágil, intuitiva y segura** para el registro, seguimiento y localización de pacientes dentro de entornos hospitalarios de alta presión como:

- 🏥 Emergencias  
- 🧪 Triaje  
- 🛏️ Unidades de Cuidados Intensivos (UCI)  
- 🏃 Clínicas de atención rápida

🔐 Nota sobre autenticación:
Dado que MediTrack está pensada para entornos de atención inmediata y colaborativa, no se ha implementado un sistema de login de usuarios. Esto se debe a que:

El enfoque está en agilidad y simplicidad de uso, evitando pasos innecesarios.

La app se asume como uso interno del personal médico que comparte un mismo dispositivo o entorno supervisado.

En versiones futuras se podría incluir login si el entorno requiere trazabilidad individual por profesional.


---

## 🎯 Objetivo General

Desarrollar una aplicación móvil que permita a los profesionales de la salud gestionar y acceder rápidamente a la información médica y de localización de los pacientes, incluyendo historial clínico detallado, citas médicas y ubicación interna en tiempo real, contribuyendo a una atención más rápida, organizada y segura en contextos de emergencia.


---

## 🎯 Objetivos Específicos

- ✅ Permitir el registro ágil de pacientes con sus datos personales y antecedentes médicos importantes (alergias, enfermedades crónicas).
- ✅ Visualizar y actualizar de forma estructurada el historial médico, incluyendo diagnósticos, tratamientos, observaciones y medicamentos prescritos.
- ✅ Gestionar citas médicas con registro de fecha, hora y motivo de atención.
- ✅ Actualizar y consultar la ubicación del paciente dentro del centro médico para mejorar la trazabilidad.
- ✅ Incluir configuraciones de accesibilidad como modo oscuro, notificaciones o alertas según la necesidad del profesional de salud.



---

## 📱 Prototipos de las Pantallas (Mockups)

| Bienvenida | Menú Principal | Registrar Paciente |
|-----------|----------------|---------------------|
| ![Bienvenida](https://raw.githubusercontent.com/AnthonyPc26/App_Gestion_De_Pacientes-GRUPO5/8b66bc825c6198e3c96d1ac90d691d8bdb99b220/Bienvenida.png) | ![Menú](https://raw.githubusercontent.com/AnthonyPc26/App_Gestion_De_Pacientes-GRUPO5/1224c5a46865d35ea19f2df9efa64bb45200ff3c/menu.png) | ![Registro](https://raw.githubusercontent.com/AnthonyPc26/App_Gestion_De_Pacientes-GRUPO5/1224c5a46865d35ea19f2df9efa64bb45200ff3c/registrarpacientes.png) |

| Registrar Médico | Historial Médico | Citas Médicas |
|------------------|------------------|---------------|
| ![Registrar Médico](https://raw.githubusercontent.com/AnthonyPc26/App_Gestion_De_Pacientes-GRUPO5/main/ImagenesReadme/registrarMedico.png) | ![Historial Médico](https://raw.githubusercontent.com/AnthonyPc26/App_Gestion_De_Pacientes-GRUPO5/main/ImagenesReadme/historialMedico.png) | ![Citas Médicas](https://raw.githubusercontent.com/AnthonyPc26/App_Gestion_De_Pacientes-GRUPO5/main/ImagenesReadme/citaMedicas.png) |

| Ubicación del Paciente | Configuración |
|------------------------|----------------|
| ![Ubicación](https://raw.githubusercontent.com/AnthonyPc26/App_Gestion_De_Pacientes-GRUPO5/main/ImagenesReadme/ubicacionPaciente.png) | ![Configuración](https://raw.githubusercontent.com/AnthonyPc26/App_Gestion_De_Pacientes-GRUPO5/main/ImagenesReadme/configuracion.png) |

---


## 🛠️ Aspectos Técnicos del Proyecto

| 📲 Frontend (App) | ⚙️ Backend (API REST) | 🗄️ Base de Datos |
|------------------|------------------------|------------------|
| Android Studio  | Node.js + Express | PostgreSQL |
| Material Design | TypeScript | TypeORM |
| Gradle | Swagger | pgAdmin |

### 🔧 Herramientas Adicionales:
- Figma (Diseño UI/UX)  
- Git y GitHub (Control de versiones)  
- Postman /  (Testeo de API)  
- Visual Studio Code

---


## 📝 Presentación de la Base de Datos

![Base de Datos](https://raw.githubusercontent.com/AnthonyPc26/App_Gestion_De_Pacientes-GRUPO5/main/ImagenesReadme/BDInventrack.jpg)

---

## 🌐 Enlace al Repositorio del Backend

🔗 [Repositorio Backend en GitHub](https://github.com/AnthonyPc26/gestion_inventarios)

---

## 👨🏻‍💻 Conclusión

**MediTrack** busca **transformar la gestión médica en zonas críticas**, ofreciendo una solución moderna, rápida y segura para el seguimiento integral de pacientes. Ideal para equipos médicos que necesitan precisión y eficiencia en contextos de alta presión. Es una herramienta pensada para **marcar la diferencia donde más importa**.

---

🎓 *Proyecto académico con enfoque profesional – ¡Hecho con pasión y dedicación!*
