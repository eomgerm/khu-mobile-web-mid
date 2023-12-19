from rest_framework import serializers

from .models import User, FCMToken


class UserSerializer(serializers.ModelSerializer):
    fcm_token = serializers.CharField(max_length=300)

    class Meta:
        model = User
        fields = ('username', 'email', 'password', 'fcm_token')

    def create(self, validated_data):
        print(validated_data)
        token = validated_data.pop('fcm_token')
        user = User.objects.create_user(
            username=validated_data['username'],
            email=validated_data['email'],
            password=validated_data['password']
        )
        FCMToken.objects.create(user=user, token=token)
        return user
