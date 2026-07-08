/*
=====================================================
Create Database
=====================================================
Script Purpose:
    This script creates a new database named 'food_delivery_db'
    using UTF-8 encoding and assigns ownership to the 'postgres'
    user.

    This script should be executed while connected to an existing
    database (such as 'postgres') because PostgreSQL does not allow
    creating a database while connected to the target database.

WARNING:
    If a database named 'food_delivery_db' already exists, this
    script will fail. Drop the existing database manually before
    executing this script if you intend to recreate it.

    Ensure that no active connections exist before dropping an
    existing database.
*/

CREATE DATABASE food_delivery_db
WITH
    OWNER = postgres
    ENCODING = 'UTF8';
