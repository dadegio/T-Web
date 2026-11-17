# Film & TV Discovery Platform — Web Engineering Project

A multi-service web application for exploring movie and television datasets, built as a university project combining **web development**, **relational and document databases**, and **data analysis**.

**Project authors:** Davide Giordano and Agnese Andriani (shared contribution, 50% each).

## What the project demonstrates

- **Service-oriented backend:** a Node.js/Express gateway coordinates responses from separate services using asynchronous requests.
- **Different persistence models:** Spring Boot, JPA and PostgreSQL manage structured catalogue data; Express and MongoDB handle dynamic information such as reviews.
- **Web frontend:** Handlebars templates, Axios and Bootstrap present content and communicate with the backend.
- **Data preparation:** Python, Pandas and Jupyter notebooks clean and transform the source datasets before import.

## Architecture

```text
                   Browser
                      |
             Handlebars frontend
                      |
              Express gateway
                  /       \
                 /         \
   Spring Boot + JPA       Express service
      PostgreSQL              MongoDB
   Catalogue data         Dynamic content
```

The gateway assembles information from multiple backends. It uses `async/await` and `Promise.all` to coordinate independent requests.

## Tech stack

| Area | Technologies |
| --- | --- |
| Web frontend | Handlebars, Axios, HTML, CSS, Bootstrap |
| API gateway | Node.js, Express |
| Catalogue service | Java, Spring Boot, JPA, PostgreSQL |
| Dynamic-data service | Node.js, Express, MongoDB |
| Data processing | Python, Pandas, Jupyter, NumPy, Matplotlib, Seaborn, GeoPandas |

## Engineering decisions

**Separation of responsibilities.** The application keeps structured catalogue data and frequently updated review data in separate services and databases.

**API composition.** The gateway aggregates responses from multiple backends so the frontend does not need to coordinate every data source independently.

**Data quality before persistence.** Dataset processing includes handling missing values and duplicates, normalizing inconsistent ratings and exporting cleaned data for database import.

## Repository guide

- [`Solutions/`](Solutions/) — project implementation
- [`Query/`](Query/) — queries and related material
- [`Report/`](Report/) — project reports

## Running the project

The repository contains multiple applications and databases. **Setup has not yet been consolidated into a single verified quick-start command.** Refer to the individual application folders under `Solutions/` for their configuration and run requirements.

Before running locally, configure the required PostgreSQL and MongoDB services and check each application's database connection settings.

## Current scope

This is an academic project and demonstration of a multi-service architecture, not a production deployment. The repository documents the implementation and data analysis; it does not currently claim a public live demo, deployment pipeline or verified end-to-end setup script.

## Why this project matters

The project covers challenges relevant to software for media platforms: combining heterogeneous content sources, structuring backend services, modeling catalogue information, and displaying data through a web interface.
