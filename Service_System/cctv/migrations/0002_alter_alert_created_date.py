# Generated by Django 4.2.7 on 2023-11-02 17:17

import datetime
from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ("cctv", "0001_initial"),
    ]

    operations = [
        migrations.AlterField(
            model_name="alert",
            name="created_date",
            field=models.DateTimeField(
                default=datetime.datetime(
                    2023, 11, 2, 17, 17, 56, 679243, tzinfo=datetime.timezone.utc
                )
            ),
        ),
    ]