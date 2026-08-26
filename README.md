# ⚔️ Kata RPG Combat – Combate por turnos en Java

> Aquí no hay diplomacia: o subes de nivel, o te quedan 50 de Health

Kata centrado en modelar por TDD las reglas de combate de un juego de rol: personajes con vida, nivel y facciones, daño y curación, rango de ataque y objetos no-personaje. Desarrollado siguiendo **TDD** con **JUnit 5 + Hamcrest**, y cobertura de tests medida con **JaCoCo**.

---

## 📑 Índice

- [Descripción](#-descripción)
- [Tecnologías](#-tecnologías)
- [Autora](#-autora)

---

## 📋 Descripción

Kata RPG Combat parte de la clase `Character`, que expone métodos para infligir y recibir daño, curarse a sí mismo, subir de nivel (con modificadores de daño según la diferencia de nivel con el objetivo), atacar dentro de un rango de combate (cuerpo a cuerpo o a distancia), unirse a facciones, y dañar objetos no vivos como props.

<details>
<summary><strong>Enunciado completo</strong></summary>

**Kata RPG Combat**

Background This is a fun kata that has the programmer building simple combat rules, as for a role-playing game (RPG). It is implemented as a sequence of iterations. The domain doesn't include a map kills or any other character sapart from their ability to damage and heal one another.

**Requiered**

- Minimum Java 21

**DevDependency**

- JUnit
- Hamcrest

**Installation**

Just clone the Kata

**Instructions**

Complete each iteration before reading the next one. It's recommended you perform this kata with a pairing partner and while writing tests.

1. Iteration One:
    - All Characters, when created, have:
        * Health, starting at 1000
        * Level, starting at 1
        * May be Alive or Dead, starting Alive (Alive may be a true/false)
    - Characters can Deal Damage to Characters.
        * Damage is subtracted from Health
        * When damage received exceeds current Health, Health becomes 0 and the character dies
    - A Character can Heal a Character.
        * Dead characters cannot be healed
        * Healing cannot raise health above 1000

2. Iteration Two:
    - A Character cannot Deal Damage to itself.
    - A Character can only Heal itself.
    - When dealing damage:
        * If the target is 5 or more Levels above the attacker, Damage is reduced by 50%
        * If the target is 5 or more levels below the attacker, Damage is increased by 50%

3. Iteration Three:
    - Characters have an attack Max Range.
    - Melee fighters have a range of 2 meters.
    - Ranged fighters have a range of 20 meters.
    - Characters must be in range to deal damage to a target.

Retrospective:
    - Are you keeping up with the requirements? Has any iteration been a big challenge?
    - Do you feel good about your design? Is it scalable and easily adapted to new requirements?
    - Is everything tested? Are you confident in your code?

4. Iteration Four:
    - Characters may belong to one or more Factions.
        * Newly created Characters belong to no Faction.
    - A Character may Join or Leave one or more Factions.
    - Players belonging to the same Faction are considered Allies.
    - Allies cannot Deal Damage to one another.
    - Allies can Heal one another.

5. Iteration Five:
    - Characters can damage non-character things (props).
        * Anything that has Health may be a target
        * These things cannot be Healed and they do not Deal Damage
        * These things do not belong to Factions; they are neutral
        * When reduced to 0 Health, things are Destroyed
        * As an example, you may create a Tree with 2000 Health

Retrospective
    - What problems did you encounter?
    - What have you learned? Any new technique or pattern?
    - Share your design with others, and get feedback on different approaches.

</details>

---


